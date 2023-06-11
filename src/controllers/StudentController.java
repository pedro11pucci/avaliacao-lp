package controllers;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import dao.StudentDAO;
import gui.gui;
import gui.components.AlunoRow;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;

import models.Student;

public class StudentController {

    static StudentDAO dao = new StudentDAO();

    public static void saveStudent() {
        Student student = new Student();

        student.setNome(gui.NomeCampo.getText());
        student.setCpf(gui.CPFCampo.getText());
        student.setData_nascimento(gui.DataCampo.getText());
        student.setPeso(Double.parseDouble(gui.PesoCampo.getText()));
        student.setAltura(Double.parseDouble(gui.AlturaCampo.getText()));

        dao.insert(student);

        gui.NomeCampo.setText("");
        gui.CPFCampo.setText("");
        gui.DataCampo.setText("");
        gui.PesoCampo.setText("");
        gui.AlturaCampo.setText("");

        showStudents();
    }

    public static void deleteStudent(AlunoRow alunoRow) {
        if (JOptionPane.showConfirmDialog(null, "Todas as informações serão deletadas",
                "Deseja excluir esse(a) Aluno(a)?", JOptionPane.YES_NO_OPTION) == 0) {
            Student student = alunoRow.student;
            dao.delete(student);

            showStudents();
        }
    }

    public static void updateStudent(AlunoRow alunoRow) {
        Student student = alunoRow.student;

        gui.NomeCampo.setText(student.getNome());
        gui.CPFCampo.setText(student.getCpf());
        gui.DataCampo.setText(student.getData_nascimento());
        gui.PesoCampo.setText(String.valueOf(student.getPeso()));
        gui.AlturaCampo.setText(String.valueOf(student.getAltura()));

        gui.EditSalvarBotão.setEnabled(true);
        gui.CadastrarBotao1.setEnabled(false);

        ActionListener salvarListener = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                student.setNome(gui.NomeCampo.getText());
                student.setCpf(gui.CPFCampo.getText());
                student.setData_nascimento(gui.DataCampo.getText());
                student.setPeso(Double.parseDouble(gui.PesoCampo.getText()));
                student.setAltura(Double.parseDouble(gui.AlturaCampo.getText()));

                dao.update(student);
                showStudents();

                gui.EditSalvarBotão.setEnabled(false);
                gui.CadastrarBotao1.setEnabled(true);

                gui.header.setText("Cadastrar aluno");

                // Remover o ActionListener após o uso
                gui.EditSalvarBotão.removeActionListener(this);
            }
        };

    // Remover qualquer ActionListener anterior antes de adicionar o novo
    ActionListener[] actionListeners = gui.EditSalvarBotão.getActionListeners();
    for (ActionListener listener : actionListeners) {
        gui.EditSalvarBotão.removeActionListener(listener);
    }

    gui.EditSalvarBotão.addActionListener(salvarListener);
    }

    public static void showStudents() {
        gui.alunosPainel.setLayout(new BoxLayout(gui.alunosPainel, BoxLayout.Y_AXIS));
        gui.alunosPainel.removeAll();

        ArrayList<Student> list = dao.getAll();
        for (Student student : list) {
            AlunoRow row = new AlunoRow(student);
            row.setVisible(true);
            gui.alunosPainel.add(row);
        }
        gui.alunosPainel.setVisible(true);
        gui.alunosPainel.revalidate();
    }
}
