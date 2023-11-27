import java.io.*;
import java.util.*;

class Student implements Comparable<Student>, Serializable {
    private String name;
    private int id;
    private double cgpa;

    public Student(String name, int id, double cgpa) {
        this.name = name;
        this.id = id;
        this.cgpa = cgpa;
    }

    @Override
    public String toString() {
        return "Student [Name: " + name + ", ID: " + id + ", CGPA: " + cgpa + "]";
    }

    @Override
    public int compareTo(Student otherStudent) {
        return Integer.compare(this.id, otherStudent.id);
    }

    public String getName() {
        return name;
    }
}

public class Main {
    public static void main(String[] args) {
        String directoryPath = "students-directory";
        File directory = new File(directoryPath);

        if (!directory.exists()) {
            directory.mkdir();
        }

        String filePath = directoryPath + "/students.txt";

        List<Student> studentsList = createStudentsList();

        System.out.println("Original List:");
        printStudentList(studentsList);

        Collections.sort(studentsList);
        System.out.println("\nStudents sorted by ID:");
        printStudentList(studentsList);

        Comparator<Student> nameComparator = Comparator.comparing(Student::getName);
        Collections.sort(studentsList, nameComparator);
        System.out.println("\nStudents sorted by Name:");
        printStudentList(studentsList);

        writeStudentsToFile(studentsList, filePath);

        List<Student> readStudentsList = readStudentsFromFile(filePath);
        System.out.println("\nStudents read from file:");
        printStudentList(readStudentsList);
    }

    private static List<Student> createStudentsList() {
        return Arrays.asList(
                new Student("Bappy", 1, 3.8),
                new Student("MD", 2, 3.5),
                new Student("Eftakher", 3, 3.9),
                new Student("Hossain", 4, 3.7),
                new Student("Razbe", 5, 3.6)
        );
    }

    private static void printStudentList(List<Student> studentsList) {
        for (Student student : studentsList) {
            System.out.println(student);
        }
    }

    private static void writeStudentsToFile(List<Student> studentsList, String filePath) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            for (Student student : studentsList) {
                objectOutputStream.writeObject(student);
            }
            System.out.println("Students written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Student> readStudentsFromFile(String filePath) {
        List<Student> readStudentsList = new ArrayList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            while (true) {
                try {
                    Student student = (Student) objectInputStream.readObject();
                    readStudentsList.add(student);
                } catch (EOFException e) {
                    break; // EOFException indicates end of file
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return readStudentsList;
    }
}
