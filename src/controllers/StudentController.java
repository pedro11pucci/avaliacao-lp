package controllers;

import java.awt.Dimension;
import java.util.ArrayList;

import dao.StudentDAO;
import gui.gui;
import gui.components.AlunoRow;
import javax.swing.BoxLayout;
import models.Student;

public class StudentController {
    
    static StudentDAO dao = new StudentDAO();
    
    public static void showStudents(){
        ArrayList<Student> list = dao.getAll();
        int contador = 0;
        
        gui.alunosPainel.setLayout(new BoxLayout(gui.alunosPainel, BoxLayout.Y_AXIS));

        for (Student student : list) {
            AlunoRow row = new AlunoRow(student);
            row.setVisible(true);
            gui.alunosPainel.add(row);
        }
        gui.alunosPainel.setVisible(true);
        gui.alunosPainel.revalidate();
    }
}
