package controllers;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import dao.StudentDAO;
import gui.gui;
import gui.components.AlunoRow;
import gui.components.ViewAluno;
import gui.components.ViewAlunoRow;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
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

        gui.header.setText("Editar aluno");

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

                gui.NomeCampo.setText("");
                gui.CPFCampo.setText("");
                gui.DataCampo.setText("");
                gui.PesoCampo.setText("");
                gui.AlturaCampo.setText("");

                gui.EditSalvarBotão.removeActionListener(this);
            }
        };

        ActionListener[] actionListeners = gui.EditSalvarBotão.getActionListeners();
        for (ActionListener listener : actionListeners) {
            gui.EditSalvarBotão.removeActionListener(listener);
        }

        gui.EditSalvarBotão.addActionListener(salvarListener);
    }

    public static void viewStudent(Student student){
        JFrame viewAluno = new ViewAluno(student);  
        viewAluno.setVisible(true);
        viewAluno.setUndecorated(true);
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

    public static void salvarArquivo(Student student) {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("C:/downloads"));
        
        double altura = student.getAltura();
        double peso = student.getPeso();
        String nome = student.getNome();
        String cpf = student.getCpf();
        double imc = (peso / (altura * altura));
        
        LocalDate dataAtual = LocalDate.now();
        String dataFormatada = dataAtual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        String interpretacao;
        if (imc < 18.5) {
            interpretacao = "Abaixo do peso";
        } else if (imc < 25) {
            interpretacao = "Peso normal";
        } else if (imc < 30) {
            interpretacao = "Sobrepeso";
        } else {
            interpretacao = "Obesidade";
        }

        String resultado = String.format("Data do cálculo: %s\nCPF: %s\nNome: %s\nÍndice IMC: %.2f\nInterpretação: %s", dataFormatada, cpf, nome, imc, interpretacao);

        try {
            int resp = fc.showSaveDialog(null);
            if (resp == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                FileWriter writer = new FileWriter(file);
                writer.write(resultado + "\n");
                writer.close();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
