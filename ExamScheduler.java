// We are the sole authors of the work in this repository.
import structure5.*;
import java.util.Scanner;
import java.util.Iterator;

public class ExamScheduler {
  private static GraphList<String, String> examsGraph = new GraphListUndirected<>();

  /**
  * Method to read in a file
  * @return a vector of student objects
  */
  public static Vector<Student> readFile() {
    Scanner s = new Scanner(System.in);

    //create a new vector of students
    Vector<Student> vecStudent = new Vector<Student>();

    //loop through the file and add populates fields of the student
    while (s.hasNext()) {
      String name = s.nextLine();
      String class1 = s.nextLine();
      String class2 = s.nextLine();
      String class3 = s.nextLine();
      String class4 = s.nextLine();

      //create student object with all the fields populated above
      Student stdnt = new Student(name, class1, class2, class3, class4);
      //add the student to the vector of students vecStudent
      vecStudent.add(stdnt);
    }
    return vecStudent;
  }

  /**
  * Adds a class (or vertex) to the examsGraph
  * @param vertex is a string representing of the name of the class that needs to be added to the examsGraph
  */
  public static void addClass(String vertex) {
    //if the examsGraph does not already contain the class, add it to the graph
    if (!examsGraph.contains(vertex)) {
      examsGraph.add(vertex);
    }
  }

  /**
  * Method to add edges between all four classes of each student
  * @param students, a vector of students
  */
  public static void addConnection(Vector<Student> students) {
    //if vertex1 and vertex2 are taken (as a class) by any one student add edge between them
    //Traverse through the vector of students
    for (int i = 0; i < students.size(); i++) {
      //store each student in a var student
      Student student = students.get(i);
      //extract their classes vector and store it in a var
      Vector<String> classes = student.getClasses();
      //traverse through the student's classes vector and add connections between each of the four classes
      for (int j = 0; j < classes.size(); j++) {
        examsGraph.addEdge((String)classes.get(j), (String)classes.get((j + 1) % 4), null);
        examsGraph.addEdge((String)classes.get(j), (String)classes.get((j + 2) % 4), null);
        examsGraph.addEdge((String)classes.get(j), (String)classes.get((j + 3) % 4), null);
      }
    }
  }

  /**
  * Method to check whether a vertex has a neighbor
  * @param vector of strings slot, representing all the time slots made for classes thus far
  * @param String vertex, representing a class
  * @return boolean indicating whether vertex has a neighbor (true) or does not (false)
  */
  public static boolean checkNeighbor(Vector<String> slot, String vertex) {
    boolean result = false;
    // traverse through the time slots
    for (int i = 0; i < slot.size(); i++) {
      //store the current slot in a var currentVertex
      String currentVertex = slot.get(i);
      //if there exists an edge between the current vertex and vertex, then vertex has a neighbor
      if (examsGraph.containsEdge(currentVertex, vertex)) {
        result = true;
      }
    }
    return result;
  }

  /**
  * Method to generate time slots for exams
  * @param students, a vector of student objects
  * @return a vector of vector of type string, representing each slot and the classes in each one
  */
  public static Vector<Vector<String>> genSlots(Vector<Student> students) {
    //create a vector of vectors of type string to store time slots of exams
    Vector<Vector<String>> allSlots = new Vector<>();
    //create iterator object to go through examsGraph
    Iterator<String> s = examsGraph.iterator();
    //while there are more vertices in examsGraph
    while (s.hasNext()) {
      //store vertex in var vertex
      String vertex = s.next();
      //if the vertex has not already been visited
      if (!examsGraph.isVisited(vertex)) {
        //add it to the slot vector as a new slot
        Vector<String> slot = new Vector<>();
        slot.add(vertex);
        //mark vertex as visited
        examsGraph.visit(vertex);
        //create another iterator object to go through vertices in examsGraph
        Iterator<String> s1 = examsGraph.iterator();
        //while there are more vertices to iterate through
        while (s1.hasNext()) {
          //store next vertex in var
          String nextVertex = s1.next();
          //if the vertex has not already been visited
          if (!examsGraph.isVisited(nextVertex)) {
            //and if the vertex is not in the slot
            if (!checkNeighbor(slot, nextVertex)) {
              //add it to the slot
              slot.add(nextVertex);
              //mark this vertex as visited as well
              examsGraph.visit(nextVertex);
            }
          }
        }
        //add each slot to the vector of vectors containing all the slots
        allSlots.add(slot);
      }
    }
    return allSlots;
  }

  /**
  * Method to print all the slots and the classes within them
  * @param vector of vector of strings slot, representing all the time slots made for classes thus far
  * @return string representation of slots, result
  */
  public static String printSlots(Vector<Vector<String>> slots) {
    //initialize empty string
    String result = " ";
    //traverse through slots
    for (int i = 0; i < slots.size(); i++) {
      //store each individual slot in var slot
      Vector<String> slot = slots.get(i);
      //append slot name and number to result
      result += "\nSlot " + (i + 1) + ": ";
      //traverse through each inidividual slot and append each class in the slot to result
      for (int j = 0; j < slot.size(); j++) {
        result += slot.get(j) + " ";
      }
    }
    return result;
  }

  /**
  * Method to check where a class exists in the vector of classes
  * @param vector of vector of strings slot, representing all the time slots made for classes thus far
  * @param String classTaken, representing the class we want the index of
  * @return integer representing the index of classTaken within the vector of classes for each slot
  */
  public static int getClassIndex(Vector<Vector<String>> slots, String classTaken) {
    //create result int and set to zero
    int result = 0;
    //traverse through slots
    for (int i = 0; i < slots.size(); i++) {
      //store each slot in a vector
      Vector<String> slot = slots.get(i);
      //if the vector contains classTaken
      if (slot.contains(classTaken)) {
        //return where classTaken is stored in vector
        result = i;
      }
    }
    //increment by one to compensate for 0-indexing
    return result + 1;
  }

  /**
  * Method to sort students by their names in alphabetical order
  * @param students, a vector of student objects
  * @param String vertex, representing a class
  * @post students is sorted in alphabetical order
  */
  public static void sortNames(Vector<Student> students) {
    //traverse through students starting from index 0
    for (int i = 0; i < students.size(); i++) {
      for (int j = i + 1; j < students.size(); j++) {
        //store the next student's name in a var
        String student1Name = students.get(i).getName();

        String student2Name = students.get(j).getName();
        //if the first student name is larger than the second student's name
        if (student1Name.compareTo(student2Name) > 0) {
          //swap the names of the students
          String temp = student1Name;
          students.get(i).setName(student2Name);
          students.get(j).setName(temp);
        }
      }
    }
  }

  /**
  * EXTENSION - creating a final exam schedule for each student, listing students in alphabetical order.
  * Method to check whether a vertex has a neighbor
  * @param vector of strings slot, representing all the time slots made for classes thus far
  * @param String vertex, representing a class
  * @return boolean indicating whether vertex has a neighbor (true) or does not (false)
  */
  public static Vector<String> genNameSlots(Vector<Vector<String>> slots, Vector<Student> students) {
    Vector<String> names = new Vector<String>();
    for (int i = 0; i < students.size(); i++) {
      Student student = students.get(i);
      //get the name of the student
      String name = student.getName() + ": ";
      //add the name to the vector
      names.add(name);
      //extract the classes
      Vector<String> classes = student.getClasses();
      //loop through the classes the student is taking
      String allClasses = "";
      for (int j = 0; j < classes.size(); j++) {
        String oneClass = classes.get(j);
        //get the slot number of the class
        int slot = getClassIndex(slots, oneClass);
        allClasses += "Slot " + slot + " ";
      }
      names.add(allClasses);

    }
    return names;
  }

  /**
  * Method to print each student's slots they must attend
  * @param vector of strings nameSlots, representing all student names whose schedules must be printed
  * @return string slot representing all of each student's slots they must attend
  */
  public static String printNameSlots(Vector<String> nameSlots) {
    //create empty string slot
    String slot = "";
    //traverse through vector
    for (int i = 0; i < nameSlots.size(); i++) {
      //add name and appropriate slots to string slot
      slot += "\n" + nameSlots.get(i); // + nameSlots.get(i+1);
    }
    return slot;
  }

  /**
  * Main method to run as a script and test ExamScheduler class
  */
  public static void main(String[] args) {
    //Read in from file and store all the students
    Vector<Student> vecStudents = readFile();
    sortNames(vecStudents);
    for (int i = 0; i < vecStudents.size(); i++) {
      Student student = vecStudents.get(i);
      // System.out.println(student.toString());
      for (int j = 0; j < student.getClasses().size(); j++) {
        addClass(student.getClasses().get(j));
      }
    }
    addConnection(vecStudents);
    Vector<Vector<String>> slotAll = genSlots(vecStudents);
    System.out.println(printSlots(slotAll));
    Vector<String> nameSlots = genNameSlots(slotAll, vecStudents);
    System.out.println("\nHere are the slots each student should attend:");
    System.out.println(printNameSlots(nameSlots));

  }
}
