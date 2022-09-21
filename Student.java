// We are the sole authors of the work in this repository.
import structure5.*;

public class Student {
  private String name;
  private Vector<String> classes;

  /**
  	* Constructor for student
  	* @param theName is a string representing the student's name
  	* @param class1 is a string representing the name of the student's first class
    * @param class2 is a string representing the name of the student's second class
    * @param class3 is a string representing the name of the student's third class
    * @param class4 is a string representing the name of the student's fourth class
  	*/
  public Student(String theName, String class1, String class2, String class3, String class4) {
    classes = new Vector<>();
    name = theName;
    classes.add(class1);
    classes.add(class2);
    classes.add(class3);
    classes.add(class4);
  }

  /**
    * toString method to represent a student object as a string
    */
  public String toString() {
    return name + ": " + classes.get(0) + ", " + classes.get(1) +  ", " + classes.get(2) + ", " + classes.get(3);
  }

  /**
    * getter method that returns a student's name
    */
  public String getName() {
    return name;
  }

  /**
    * setter method that can change a student's name
    * @param newName is a string representing the new name of the student
    */
  public void setName(String newName) {
    this.name = newName;
  }

  /**
    * getter method that returns a vector containing all of a student's classes
    */
  public Vector<String> getClasses() {
    return classes;
  }

  /**
    * getter method that returns a string representing a student's first class
    */
  public String class1() {
    return classes.get(0);
  }

  /**
    * getter method that returns a string representing a student's second class
    */
  public String class2() {
    return classes.get(1);
  }

  /**
    * getter method that returns a string representing a student's third class
    */
  public String class3() {
    return classes.get(2);
  }

  /**
    * getter method that returns a string representing a student's fourth class
    */
  public String class4() {
    return classes.get(3);
  }
}
