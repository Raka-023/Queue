import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Queue;
import com.sun.speech.freetts.*;

public class queue_linkedlist {

    static class Antrian {
        int nomor;
        String nama;

        Antrian(int nomor, String nama) {
            this.nomor = nomor;
            this.nama = nama;
        }

        @Override
        public String toString() {
            return "No: " + nomor + " | Nama: " + nama;
        }

    }

    private Queue<Antrian> queue = new LinkedList<>();
    private int nomorCounter = 1;

    private JTextArea textArea;
    private JLabel labelPanggilan;

    public queue_linkedlist() {
        JFrame frame = new JFrame("Simulasi Antrian Bank");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Text Area
        textArea = new JTextArea();
        textArea.setEditable(false);
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Panel Button
        JPanel panel = new JPanel();

        JButton btnAmbil = new JButton("Ambil Antrian");
        JButton btnTampil = new JButton("Tampilkan Antrian");
        JButton btnPanggil = new JButton("Panggil Antrian");

        panel.add(btnAmbil);
        panel.add(btnTampil);
        panel.add(btnPanggil);

        frame.add(panel, BorderLayout.SOUTH);

        // Label atas
        labelPanggilan = new JLabel("Belum ada panggilan", JLabel.CENTER);
        labelPanggilan.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(labelPanggilan, BorderLayout.NORTH);

        // Event Ambil Antrian
        btnAmbil.addActionListener(e -> {
            String nama = JOptionPane.showInputDialog(frame, "Masukkan Nama:");
            if (nama != null && !nama.trim().isEmpty()) {
                Antrian a = new Antrian(nomorCounter++, nama);
                queue.add(a);
                JOptionPane.showMessageDialog(frame, "Nomor Antrian Anda: " + a.nomor);
            }
        });

        // Event Tampilkan
        btnTampil.addActionListener(e -> {
            textArea.setText("");
            if (queue.isEmpty()) {
                textArea.setText("Antrian kosong");
            } else {
                for (Antrian a : queue) {
                    textArea.append(a.toString() + "\n");
                }
            }
        });

        // Event Panggil
        btnPanggil.addActionListener(e -> {
            if (queue.isEmpty()) {
                labelPanggilan.setText("Tidak ada antrian");
                return;
            }

            Antrian a = queue.poll();
            String panggilan = "Memanggil nomor " + a.nomor + ", atas nama " + a.nama;
            labelPanggilan.setText(panggilan);

            // Voice sederhana (pakai beep)
          speak("Nomor " + a.nomor + ", atas nama " + a.nama + ",  please go to counter");
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(queue_linkedlist::new);
    }

    public void speak(String text) {
    System.setProperty("freetts.voices",
        "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

    VoiceManager vm = VoiceManager.getInstance();
    Voice voice = vm.getVoice("kevin16");

    if (voice == null) {
        System.out.println("Voice tidak ditemukan!");
        return;
    }

    voice.allocate();
    voice.speak(text);
    voice.deallocate();
}
}
