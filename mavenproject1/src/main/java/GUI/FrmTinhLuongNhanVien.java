/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import dao.BangLuongNVDao;
import dao.NhanVienDao;
import dao.PhongBanDao;
import entity.BangLuongNhanVien;
import entity.NhanVien;
import java.text.DecimalFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

/**
 *
 * @author huylauri
 */
public class FrmTinhLuongNhanVien extends javax.swing.JPanel {
    private NhanVienDao nhanVienDao = new NhanVienDao();
    private PhongBanDao phongBanDao = new PhongBanDao();
    private BangLuongNVDao bangLuongNVDao = new BangLuongNVDao();
            
    private DefaultTableModel modelDsNhanVien;
    private DefaultTableModel modelDsBangLuong;
    /**
     * Creates new form FrmTinhLuongNhanVien
     */
    public FrmTinhLuongNhanVien() {
        initComponents();
        for (int i = 1; i <= 12; i++)
            cmbThang.addItem(String.format("%02d", i));
        for (int year = 2015; year <= 2024; year++)
            cmbNam.addItem(String.valueOf(year));

        loadDataCmbPhongBanLoc();
        initTable();
        loadDataTblDsNhanVien();
        loadDataTblDsBangLuong();
    }

    private void loadDataTblDsNhanVien() {
        try {
            List<NhanVien> list = nhanVienDao.layDSNhanVien();
            modelDsNhanVien.setRowCount(0);
            for (NhanVien nv : list) {
                {
                    modelDsNhanVien.addRow(new Object[]{
                        nv.getMaNhanVien(), nv.getTenNhanVien(), nv.getNgaySinh(), nv.getPhongBan().getTenPhongBan(), nv.getChucVu()
                    });
                }
            }
            modelDsNhanVien.fireTableDataChanged();
        } catch (Exception e) {
        }
    }
    
    private void loadDataTblDsNhanVien(List<NhanVien> list, DefaultTableModel model) {
        try {
            model.setRowCount(0);
            for (NhanVien nv : list) {
                {
                    model.addRow(new Object[]{
                       nv.getMaNhanVien(), nv.getTenNhanVien(), nv.getNgaySinh(), nv.getPhongBan().getTenPhongBan(), nv.getChucVu()
                    });
                }
            }
            model.fireTableDataChanged();
        } catch (Exception e) {
        }
    }
    
    private void loadDataTblDsBangLuong() {
        try {
            List<BangLuongNhanVien> list = bangLuongNVDao.layDsBangLuongNV();
            modelDsBangLuong.setRowCount(0);
            for (BangLuongNhanVien blnv : list) {
                {
                    NhanVien nv = blnv.getNhanVien();
                    DecimalFormat decimalFormat = new DecimalFormat("#");
                    modelDsBangLuong.addRow(new Object[]{
                        blnv.getMaBangLuong(), blnv.getNhanVien().getMaNhanVien(), blnv.getNhanVien().getTenNhanVien(), blnv.getNhanVien().getPhongBan().getTenPhongBan(), 
                        blnv.getNhanVien().getHeSoLuong(), blnv.getThangLuong(), blnv.getNamLuong(), blnv.getSoNgayLam(), decimalFormat.format(nv.getLuongCoBan()), blnv.getNhanVien().getPhuCap(), decimalFormat.format(blnv.getTongLuong())
                    });
                }
            }
            modelDsBangLuong.fireTableDataChanged();
        } catch (Exception e) {
        }
    }
    
    private void initTable() {
        modelDsNhanVien = new DefaultTableModel();
        modelDsBangLuong = new DefaultTableModel();
        modelDsNhanVien.setColumnIdentifiers(new String[]{"Mã NV", "Tên NV", "Ngày sinh", "Phòng ban", "Chức vụ"});
        modelDsBangLuong.setColumnIdentifiers(new String[]{"Mã Bảng lương", "Mã Nhân viên", "Tên Nhân viên", "Phòng ban", "Hệ số lương",
            "Tháng lương", "Năm lương", "Số ngày làm", "Lương cơ bản", "Phụ cấp", "Tổng lương"});
        tblDsNhanVien.setModel(modelDsNhanVien);
        tblDsTinhLuong.setModel(modelDsBangLuong);
    }
    
    private void loadDataCmbPhongBanLoc() {
        try {
            List<String> data = phongBanDao.layDsTenPhongBan();
            DefaultComboBoxModel<String> newModel = new DefaultComboBoxModel<>(data.toArray((new String[data.size()])));
            cmbPhongBanLoc.setModel(newModel);
        } catch (Exception ex) {
            Logger.getLogger(FrmChamCongNhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static int demNgayChuNhatThangNam(int nam, int thang) {
        LocalDate firstDayOfMonth = LocalDate.of(nam, thang, 1);
        int daysInMonth = firstDayOfMonth.getMonth().length(firstDayOfMonth.isLeapYear());
        int count = 0;
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = LocalDate.of(nam, thang, day);
            if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                count++;
            }
        }
        return count;
    }
    
//    Tính tổng tiền lương theo mã Nhân viên
    private double tinhTongLuongTheoMaNV(String maNhanVien, int thang, int nam, Map<Double, Double> map) {
        double tongLuong = 0.0;
        try {
            int soNgayCongChuan = 31 - demNgayChuNhatThangNam(nam, thang);
            NhanVien nv = nhanVienDao.layNVTheoMa(maNhanVien);
            Double luongCoBan = nv.getLuongCoBan();
      
//            Map<Double, Double> laySoNgayLamTheoMaNV = bangLuongNVDao.laySoNgayLamTheoMaNV("NV_0001");
            for (Map.Entry<Double, Double> entry : map.entrySet()) {
                double heSoLuong = entry.getKey();
                double soNgayLam = entry.getValue();
                double luong = heSoLuong * (soNgayLam / soNgayCongChuan) * luongCoBan;
                tongLuong += luong;
            }
        } catch (Exception ex) {
            Logger.getLogger(FrmTinhLuongNhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tongLuong;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTieuDe = new javax.swing.JLabel();
        pnlLocDsNhanVien = new javax.swing.JPanel();
        lblPhongBanLoc = new javax.swing.JLabel();
        cmbPhongBanLoc = new javax.swing.JComboBox<>();
        btnLoc = new javax.swing.JButton();
        btnBoLoc = new javax.swing.JButton();
        pnlDsNhanVien = new javax.swing.JPanel();
        scrDsNhanVien = new javax.swing.JScrollPane();
        tblDsNhanVien = new javax.swing.JTable();
        pnlThongTinTinhLuong = new javax.swing.JPanel();
        txtSoNgayLam = new javax.swing.JTextField();
        lblNam = new javax.swing.JLabel();
        lblThang = new javax.swing.JLabel();
        cmbNam = new javax.swing.JComboBox<>();
        lblSoNgayLam = new javax.swing.JLabel();
        lblTongLuong = new javax.swing.JLabel();
        txtTongLuong = new javax.swing.JTextField();
        txtMaNhanVien = new javax.swing.JTextField();
        lblMaNhanVien = new javax.swing.JLabel();
        cmbThang = new javax.swing.JComboBox<>();
        lblNhanVien = new javax.swing.JLabel();
        cmbNhanVien = new javax.swing.JComboBox<>();
        pnlNutChucNang = new javax.swing.JPanel();
        btnTinhLuong = new javax.swing.JButton();
        btnXoaTinhLuong = new javax.swing.JButton();
        btnBoChon = new javax.swing.JButton();
        btnXuatExcel = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();
        pnlDsTinhLuong = new javax.swing.JPanel();
        scrDsTinhLuong = new javax.swing.JScrollPane();
        tblDsTinhLuong = new javax.swing.JTable();

        lblTieuDe.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTieuDe.setForeground(new java.awt.Color(51, 0, 255));
        lblTieuDe.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTieuDe.setText("TÍNH LƯƠNG NHÂN VIÊN HÀNH CHÍNH");
        lblTieuDe.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        pnlLocDsNhanVien.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lọc danh sách nhân viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        pnlLocDsNhanVien.setMaximumSize(new java.awt.Dimension(100, 100));

        lblPhongBanLoc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPhongBanLoc.setText("Phòng ban:");

        cmbPhongBanLoc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbPhongBanLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPhongBanLocActionPerformed(evt);
            }
        });

        btnLoc.setBackground(java.awt.SystemColor.controlHighlight);
        btnLoc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnLoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interface/Images/icon-loc-nho.png"))); // NOI18N
        btnLoc.setText("Lọc");
        btnLoc.setMargin(new java.awt.Insets(0, 2, 0, 2));
        btnLoc.setMaximumSize(new java.awt.Dimension(115, 37));
        btnLoc.setMinimumSize(new java.awt.Dimension(115, 37));
        btnLoc.setPreferredSize(new java.awt.Dimension(125, 37));
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
            }
        });

        btnBoLoc.setBackground(java.awt.SystemColor.controlHighlight);
        btnBoLoc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnBoLoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interface/Images/icon-boloc-nho.png"))); // NOI18N
        btnBoLoc.setText("Bỏ lọc");
        btnBoLoc.setMargin(new java.awt.Insets(0, 2, 0, 2));
        btnBoLoc.setMaximumSize(new java.awt.Dimension(115, 37));
        btnBoLoc.setMinimumSize(new java.awt.Dimension(115, 37));
        btnBoLoc.setPreferredSize(new java.awt.Dimension(125, 37));
        btnBoLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBoLocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlLocDsNhanVienLayout = new javax.swing.GroupLayout(pnlLocDsNhanVien);
        pnlLocDsNhanVien.setLayout(pnlLocDsNhanVienLayout);
        pnlLocDsNhanVienLayout.setHorizontalGroup(
            pnlLocDsNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLocDsNhanVienLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblPhongBanLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(cmbPhongBanLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(btnLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnBoLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(107, 107, 107))
        );
        pnlLocDsNhanVienLayout.setVerticalGroup(
            pnlLocDsNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLocDsNhanVienLayout.createSequentialGroup()
                .addGroup(pnlLocDsNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlLocDsNhanVienLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlLocDsNhanVienLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(pnlLocDsNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPhongBanLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbPhongBanLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBoLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pnlDsNhanVien.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách Nhân viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        scrDsNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scrDsNhanVienMouseClicked(evt);
            }
        });

        tblDsNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblDsNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDsNhanVienMouseClicked(evt);
            }
        });
        scrDsNhanVien.setViewportView(tblDsNhanVien);

        javax.swing.GroupLayout pnlDsNhanVienLayout = new javax.swing.GroupLayout(pnlDsNhanVien);
        pnlDsNhanVien.setLayout(pnlDsNhanVienLayout);
        pnlDsNhanVienLayout.setHorizontalGroup(
            pnlDsNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrDsNhanVien)
        );
        pnlDsNhanVienLayout.setVerticalGroup(
            pnlDsNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrDsNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        pnlThongTinTinhLuong.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin tính lương", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        pnlThongTinTinhLuong.setMaximumSize(new java.awt.Dimension(100, 100));

        txtSoNgayLam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoNgayLamActionPerformed(evt);
            }
        });

        lblNam.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblNam.setText("Năm:");

        lblThang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblThang.setText("Tháng:");

        cmbNam.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmbNam.setName(""); // NOI18N
        cmbNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbNamActionPerformed(evt);
            }
        });

        lblSoNgayLam.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblSoNgayLam.setText("Số ngày làm:");

        lblTongLuong.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblTongLuong.setText("Tổng lương:");

        txtTongLuong.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTongLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTongLuongActionPerformed(evt);
            }
        });

        txtMaNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNhanVienActionPerformed(evt);
            }
        });

        lblMaNhanVien.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblMaNhanVien.setText("Mã NV:");

        cmbThang.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmbThang.setName(""); // NOI18N
        cmbThang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbThangActionPerformed(evt);
            }
        });

        lblNhanVien.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblNhanVien.setText("Nhân viên:");

        cmbNhanVien.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmbNhanVien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        cmbNhanVien.setName(""); // NOI18N
        cmbNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbNhanVienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlThongTinTinhLuongLayout = new javax.swing.GroupLayout(pnlThongTinTinhLuong);
        pnlThongTinTinhLuong.setLayout(pnlThongTinTinhLuongLayout);
        pnlThongTinTinhLuongLayout.setHorizontalGroup(
            pnlThongTinTinhLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinTinhLuongLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlThongTinTinhLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblMaNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSoNgayLam, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                    .addComponent(lblTongLuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(pnlThongTinTinhLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMaNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(txtTongLuong)
                    .addComponent(txtSoNgayLam, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(pnlThongTinTinhLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblNam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblThang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
                .addGroup(pnlThongTinTinhLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbThang, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbNam, javax.swing.GroupLayout.Alignment.TRAILING, 0, 170, Short.MAX_VALUE)
                    .addComponent(cmbNhanVien, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlThongTinTinhLuongLayout.setVerticalGroup(
            pnlThongTinTinhLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinTinhLuongLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlThongTinTinhLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlThongTinTinhLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinTinhLuongLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addGroup(pnlThongTinTinhLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTongLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTongLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlThongTinTinhLuongLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlThongTinTinhLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSoNgayLam, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSoNgayLam, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongTinTinhLuongLayout.createSequentialGroup()
                        .addGroup(pnlThongTinTinhLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblThang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbThang, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                        .addGap(16, 16, 16)
                        .addGroup(pnlThongTinTinhLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbNam, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNam, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        btnTinhLuong.setBackground(new java.awt.Color(0, 204, 51));
        btnTinhLuong.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTinhLuong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interface/Images/them.png"))); // NOI18N
        btnTinhLuong.setText("Tính lương");
        btnTinhLuong.setMargin(new java.awt.Insets(2, 2, 3, 2));
        btnTinhLuong.setMaximumSize(new java.awt.Dimension(115, 37));
        btnTinhLuong.setMinimumSize(new java.awt.Dimension(115, 37));
        btnTinhLuong.setPreferredSize(new java.awt.Dimension(125, 37));
        btnTinhLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTinhLuongActionPerformed(evt);
            }
        });

        btnXoaTinhLuong.setBackground(new java.awt.Color(255, 102, 102));
        btnXoaTinhLuong.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXoaTinhLuong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interface/Images/icon-xoachamcong.png"))); // NOI18N
        btnXoaTinhLuong.setText("Xoá Tính lương");
        btnXoaTinhLuong.setMargin(new java.awt.Insets(2, 2, 3, 2));
        btnXoaTinhLuong.setPreferredSize(new java.awt.Dimension(125, 23));

        btnBoChon.setBackground(new java.awt.Color(0, 206, 245));
        btnBoChon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnBoChon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interface/Images/icon-bochon.png"))); // NOI18N
        btnBoChon.setText("Bỏ chọn");
        btnBoChon.setMargin(new java.awt.Insets(2, 2, 3, 2));
        btnBoChon.setPreferredSize(new java.awt.Dimension(125, 22));
        btnBoChon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBoChonActionPerformed(evt);
            }
        });

        btnXuatExcel.setBackground(new java.awt.Color(231, 242, 242));
        btnXuatExcel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXuatExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interface/Images/icon-XuatExcel.png"))); // NOI18N
        btnXuatExcel.setText("Xuất Excel");
        btnXuatExcel.setMargin(new java.awt.Insets(2, 0, 3, 0));
        btnXuatExcel.setPreferredSize(new java.awt.Dimension(125, 22));

        btnThoat.setBackground(new java.awt.Color(252, 33, 30));
        btnThoat.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interface/Images/icon-thoat.png"))); // NOI18N
        btnThoat.setText("Thoát");
        btnThoat.setMargin(new java.awt.Insets(2, 2, 3, 2));
        btnThoat.setPreferredSize(new java.awt.Dimension(125, 23));

        javax.swing.GroupLayout pnlNutChucNangLayout = new javax.swing.GroupLayout(pnlNutChucNang);
        pnlNutChucNang.setLayout(pnlNutChucNangLayout);
        pnlNutChucNangLayout.setHorizontalGroup(
            pnlNutChucNangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNutChucNangLayout.createSequentialGroup()
                .addContainerGap(207, Short.MAX_VALUE)
                .addComponent(btnTinhLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(btnXoaTinhLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(btnBoChon, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(btnXuatExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(208, Short.MAX_VALUE))
        );
        pnlNutChucNangLayout.setVerticalGroup(
            pnlNutChucNangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNutChucNangLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(pnlNutChucNangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTinhLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaTinhLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBoChon, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXuatExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        pnlDsTinhLuong.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách tính lương", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        tblDsTinhLuong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scrDsTinhLuong.setViewportView(tblDsTinhLuong);

        javax.swing.GroupLayout pnlDsTinhLuongLayout = new javax.swing.GroupLayout(pnlDsTinhLuong);
        pnlDsTinhLuong.setLayout(pnlDsTinhLuongLayout);
        pnlDsTinhLuongLayout.setHorizontalGroup(
            pnlDsTinhLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrDsTinhLuong)
        );
        pnlDsTinhLuongLayout.setVerticalGroup(
            pnlDsTinhLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrDsTinhLuong, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlNutChucNang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlThongTinTinhLuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlLocDsNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 565, Short.MAX_VALUE))
                        .addGap(20, 20, 20)
                        .addComponent(pnlDsNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(pnlDsTinhLuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTieuDe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblTieuDe)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlLocDsNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(pnlThongTinTinhLuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(pnlDsNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlNutChucNang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlDsTinhLuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmbPhongBanLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPhongBanLocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbPhongBanLocActionPerformed

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        try {
            List<NhanVien> list = nhanVienDao.layDSNhanVien(String.valueOf(cmbPhongBanLoc.getSelectedItem()));
            initTable();
            loadDataTblDsNhanVien(list, modelDsNhanVien);
        } catch (Exception ex) {
            Logger.getLogger(FrmChamCongNhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnLocActionPerformed

    private void btnBoLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBoLocActionPerformed
        initTable();
        loadDataTblDsNhanVien();
    }//GEN-LAST:event_btnBoLocActionPerformed

    private void tblDsNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDsNhanVienMouseClicked
        int row = tblDsNhanVien.getSelectedRow();
        String maNVChon = modelDsNhanVien.getValueAt(row, 0).toString();
        try {
            NhanVien nv = nhanVienDao.layNVTheoMa(maNVChon);
            txtMaNhanVien.setText(String.valueOf(nv.getMaNhanVien()));
            String tenNV = nv.getTenNhanVien();
            cmbNhanVien.removeAllItems();
            cmbNhanVien.addItem("Tất cả");
            cmbNhanVien.addItem(tenNV);
            cmbNhanVien.setSelectedItem(tenNV);

            int thangLuong = Integer.parseInt(cmbThang.getSelectedItem().toString());
            int namLuong = Integer.parseInt(cmbNam.getSelectedItem().toString());
            Map<Double, Double> map = bangLuongNVDao.laySoNgayLamTheoMaNV(maNVChon, thangLuong, namLuong);
            double soNgayLam = 0;
            for (double value : map.values()) {
                soNgayLam += value;
            }
            txtSoNgayLam.setText(String.valueOf(soNgayLam));
            
            Double tongLuong = tinhTongLuongTheoMaNV(maNVChon, thangLuong, namLuong, map);
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            String formattedTongLuong = decimalFormat.format(tongLuong);

            txtTongLuong.setText(String.valueOf(formattedTongLuong));
            
        } catch (Exception ex) {
            Logger.getLogger(FrmQuanLyNhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tblDsNhanVienMouseClicked

    private void scrDsNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scrDsNhanVienMouseClicked

    }//GEN-LAST:event_scrDsNhanVienMouseClicked

    private void txtSoNgayLamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoNgayLamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoNgayLamActionPerformed

    private void cmbNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbNamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbNamActionPerformed

    private void txtTongLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTongLuongActionPerformed

    private void txtMaNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNhanVienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNhanVienActionPerformed

    private void cmbThangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbThangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbThangActionPerformed

    private void btnTinhLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTinhLuongActionPerformed
        try {
            int row = tblDsNhanVien.getSelectedRow();
            String maNVChon = modelDsNhanVien.getValueAt(row, 0).toString();
            int thangLuong = Integer.parseInt(cmbThang.getSelectedItem().toString());
            int namLuong = Integer.parseInt(cmbNam.getSelectedItem().toString());
//            Map<Double, Double> laySoNgayLamTheoMaNV = bangLuongNVDao.laySoNgayLamTheoMaNV(maNVChon);
        } catch (Exception ex) {
            Logger.getLogger(FrmTinhLuongNhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnTinhLuongActionPerformed

    private void btnBoChonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBoChonActionPerformed
//        xoaRong();
    }//GEN-LAST:event_btnBoChonActionPerformed

    private void cmbNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbNhanVienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbNhanVienActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBoChon;
    private javax.swing.JButton btnBoLoc;
    private javax.swing.JButton btnLoc;
    private javax.swing.JButton btnThoat;
    private javax.swing.JButton btnTinhLuong;
    private javax.swing.JButton btnXoaTinhLuong;
    private javax.swing.JButton btnXuatExcel;
    private javax.swing.JComboBox<String> cmbNam;
    private javax.swing.JComboBox<String> cmbNhanVien;
    private javax.swing.JComboBox<String> cmbPhongBanLoc;
    private javax.swing.JComboBox<String> cmbThang;
    private javax.swing.JLabel lblMaNhanVien;
    private javax.swing.JLabel lblNam;
    private javax.swing.JLabel lblNhanVien;
    private javax.swing.JLabel lblPhongBanLoc;
    private javax.swing.JLabel lblSoNgayLam;
    private javax.swing.JLabel lblThang;
    private javax.swing.JLabel lblTieuDe;
    private javax.swing.JLabel lblTongLuong;
    private javax.swing.JPanel pnlDsNhanVien;
    private javax.swing.JPanel pnlDsTinhLuong;
    private javax.swing.JPanel pnlLocDsNhanVien;
    private javax.swing.JPanel pnlNutChucNang;
    private javax.swing.JPanel pnlThongTinTinhLuong;
    private javax.swing.JScrollPane scrDsNhanVien;
    private javax.swing.JScrollPane scrDsTinhLuong;
    private javax.swing.JTable tblDsNhanVien;
    private javax.swing.JTable tblDsTinhLuong;
    private javax.swing.JTextField txtMaNhanVien;
    private javax.swing.JTextField txtSoNgayLam;
    private javax.swing.JTextField txtTongLuong;
    // End of variables declaration//GEN-END:variables
}
