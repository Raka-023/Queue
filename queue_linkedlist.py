import tkinter as tk
from tkinter import simpledialog, messagebox
from collections import deque
from gtts import gTTS
from playsound import playsound
import os




def speak(text):
    tts = gTTS(text=text, lang='id')  # bahasa Indonesia
    tts.save("suara.mp3")
    playsound("suara.mp3")
    os.remove("suara.mp3")  # hapus biar gak numpuk

class Antrian:
    def __init__(self, nomor, nama):
        self.nomor = nomor
        self.nama = nama

    def __str__(self):
        return f"No: {self.nomor} | Nama: {self.nama}"

class QueueApp:
    def __init__(self, root):
        self.root = root
        self.root.title("Simulasi Antrian Bank")
        self.root.geometry("500x400")

        self.queue = deque()
        self.nomor_counter = 1

        # Label atas
        self.label = tk.Label(root, text="Belum ada panggilan", font=("Arial", 14))
        self.label.pack(pady=10)

        # Text area
        self.text_area = tk.Text(root, height=10)
        self.text_area.pack(padx=10, pady=10)

        # Frame tombol
        frame = tk.Frame(root)
        frame.pack()

        btn_ambil = tk.Button(frame, text="Ambil Antrian", command=self.ambil_antrian)
        btn_tampil = tk.Button(frame, text="Tampilkan Antrian", command=self.tampilkan_antrian)
        btn_panggil = tk.Button(frame, text="Panggil Antrian", command=self.panggil_antrian)

        btn_ambil.grid(row=0, column=0, padx=5)
        btn_tampil.grid(row=0, column=1, padx=5)
        btn_panggil.grid(row=0, column=2, padx=5)

    def ambil_antrian(self):
        nama = simpledialog.askstring("Input", "Masukkan Nama:")
        if nama:
            a = Antrian(self.nomor_counter, nama)
            self.queue.append(a)
            messagebox.showinfo("Info", f"Nomor Antrian Anda: {a.nomor}")
            self.nomor_counter += 1

    def tampilkan_antrian(self):
        self.text_area.delete("1.0", tk.END)
        if not self.queue:
            self.text_area.insert(tk.END, "Antrian kosong")
        else:
            for a in self.queue:
                self.text_area.insert(tk.END, str(a) + "\n")

    def panggil_antrian(self):
        if not self.queue:
            self.label.config(text="Tidak ada antrian")
            return

        a = self.queue.popleft()
        teks = f"Memanggil nomor {a.nomor}, atas nama {a.nama}"
        self.label.config(text=teks)

        # 🔊 Voice
        speak(f"Nomor {a.nomor}, {a.nama}, silahkan menuju loket")

# Run aplikasi
root = tk.Tk()
app = QueueApp(root)
root.mainloop()