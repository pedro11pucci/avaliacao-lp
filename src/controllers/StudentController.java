package controllers;

import java.awt.Dimension;
import java.util.ArrayList;

import dao.StudentDAO;
import gui.gui;
import gui.components.AlunoRow;
import models.Student;

public class StudentController {
    
    static StudentDAO dao = new StudentDAO();
    
    public static void showStudents(){
        ArrayList<Student> list = dao.getAll();
        int contador = 0;

        for (Student student : list) {
            AlunoRow row = new AlunoRow(student);
            row.getViewButton().setVisible(false);
            row.getEdiButton().setVisible(false);
            row.getDeleteButton().setVisible(false);
            gui.AlunosPainel.add(row);
            gui.AlunosPainel.repaint();

            contador++;
        }

        gui.AlunosPainel.setPreferredSize(new Dimension(501, 50*contador));
        gui.AlunosPainel.revalidate();
        gui.AlunosPainel.repaint();
    }
}
