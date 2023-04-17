/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import entity.PhanDoan;
import entity.SanPham;
import dao.PhanDoanDao;
import dao.SanPhamDao;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Chanhne
 */
public class FrmQuanLyPhanDoan extends javax.swing.JPanel {

    private final PhanDoanDao phanDoanDao;

    public FrmQuanLyPhanDoan() {
        phanDoanDao = new PhanDoanDao();
        initComponents();
        loadTableData();

    }

    private void checkInputData() {
        txtMaSanPham.setEnabled(false);
        txtTenSanPham.setEnabled(false);
    }

    private void cb() {
        PhanDoanDao phanDoanDao = new PhanDoanDao();
        List<String> tenPhanDoanList = phanDoanDao.getTenPhanDoanList();
        jComboBoxPhanDoanYeuCau.removeAllItems(); // Clear existing items
        jComboBoxPhanDoanYeuCau.addItem("Không");
        for (String tenPhanDoan : tenPhanDoanList) {
            jComboBoxPhanDoanYeuCau.addItem(tenPhanDoan); // Add new items
        }
    }

    private void loadTableData() {
        SanPhamDao sanPhamDAO = new SanPhamDao();
        List<SanPham> sanPhams = sanPhamDAO.getAllSanPham();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Mã sản phẩm");
        model.addColumn("Tên sản phẩm");
        model.addColumn("Loại sản phẩm");
        model.addColumn("Màu sắc");
        model.addColumn("Ngày hoàn thành");
        model.addColumn("Trạng thái");

        for (SanPham sanPham : sanPhams) {
            model.addRow(new Object[]{
                sanPham.getMaSanPham(),
                sanPham.getTenSanPham(),
                sanPham.getLoaiSanPham(),
                sanPham.getMauSac(),
                sanPham.getNgayHoanThanh(),
                sanPham.getTrangThai()
            });
        }
        tblDanhSachSanPham.setModel(model);
        checkInputData();
    }

    private void xemPhanDoan() {
        String maSanPham = txtMaSanPham.getText();
        if (maSanPham.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            List<PhanDoan> phanDoanList = phanDoanDao.getPhanDoanByMaSanPham(maSanPham);
            if (phanDoanList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy phân đoạn cho sản phẩm này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                xoaDuLieuTrongBang();
            } else {
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Mã phân đoạn");
                model.addColumn("Tên phân đoạn");
                model.addColumn("Mã sản phẩm");
                model.addColumn("Tên sản phẩm");
                model.addColumn("Giá phân đoạn");
                model.addColumn("Phân đoạn yêu cầu");
                for (PhanDoan phanDoan : phanDoanList) {
                    model.addRow(new Object[]{
                        phanDoan.getMaPhanDoan(),
                        phanDoan.getTenPhanDoan(),
                        phanDoan.getSanPham().getMaSanPham(),
                        phanDoan.getSanPham().getTenSanPham(),
                        phanDoan.getGiaPhanDoan(),
                        phanDoan.getPhanDoanYeuCau()
                    });
                }
                tblDanhSachPhanDoan.setModel(model);
                cb();
            }
        }
    }

    private void xoaRong() {
        txtMaPhanDoan.setText("");
        txtTenPhanDoan.setText("");
        txtMaSanPham.setText("");
        txtTenSanPham.setText("");
        txtGiaPhanDoan.setText("");
    }

    private void xoaDuLieuTrongBang() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachPhanDoan.getModel();
        model.setRowCount(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTieuDe1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblDanhSachSanPham = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        lblMaSanPham = new javax.swing.JLabel();
        txtMaSanPham = new javax.swing.JTextField();
        lblMaSanPham1 = new javax.swing.JLabel();
        txtTenSanPham = new javax.swing.JTextField();
        lblMaSanPham2 = new javax.swing.JLabel();
        txtMaPhanDoan = new javax.swing.JTextField();
        lblMaSanPham3 = new javax.swing.JLabel();
        txtGiaPhanDoan = new javax.swing.JTextField();
        lblMaSanPham4 = new javax.swing.JLabel();
        lblMaSanPham5 = new javax.swing.JLabel();
        txtTenPhanDoan = new javax.swing.JTextField();
        jComboBoxPhanDoanYeuCau = new javax.swing.JComboBox<>();
        pnlNutChucNang = new javax.swing.JPanel();
        btnXem = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnXoaPhanDoan = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        btnXuatExcel = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblDanhSachPhanDoan = new javax.swing.JTable();

        setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        setPreferredSize(new java.awt.Dimension(1120, 824));

        lblTieuDe1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTieuDe1.setForeground(new java.awt.Color(51, 0, 255));
        lblTieuDe1.setText("PHÂN ĐOẠN");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        tblDanhSachSanPham.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDanhSachSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachSanPhamMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblDanhSachSanPham);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                .addContainerGap())
        );

        lblMaSanPham.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblMaSanPham.setText("Mã sản phẩm:");

        lblMaSanPham1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblMaSanPham1.setText("Tên sản phẩm:");

        lblMaSanPham2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblMaSanPham2.setText("Mã phân đoạn:");

        txtMaPhanDoan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMaPhanDoanFocusLost(evt);
            }
        });
        txtMaPhanDoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaPhanDoanActionPerformed(evt);
            }
        });

        lblMaSanPham3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblMaSanPham3.setText("Giá phân đoạn:");

        txtGiaPhanDoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaPhanDoanActionPerformed(evt);
            }
        });

        lblMaSanPham4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblMaSanPham4.setText("Phân đoạn yêu cầu:");

        lblMaSanPham5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblMaSanPham5.setText("Tên phân đoạn:");

        txtTenPhanDoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenPhanDoanActionPerformed(evt);
            }
        });

        jComboBoxPhanDoanYeuCau.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Không" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMaSanPham3, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaSanPham2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMaSanPham)
                    .addComponent(txtGiaPhanDoan, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaPhanDoan, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblMaSanPham1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaSanPham4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMaSanPham5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTenSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                    .addComponent(jComboBoxPhanDoanYeuCau, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenPhanDoan))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtMaSanPham, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenSanPham, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaSanPham1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaSanPham, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtMaPhanDoan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTenPhanDoan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblMaSanPham5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblMaSanPham2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtGiaPhanDoan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblMaSanPham4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBoxPhanDoanYeuCau, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblMaSanPham3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        btnXem.setBackground(new java.awt.Color(204, 255, 204));
        btnXem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interface/Images/icon-CapNhat.png"))); // NOI18N
        btnXem.setText("Xem");
        btnXem.setMargin(new java.awt.Insets(2, 2, 3, 2));
        btnXem.setMaximumSize(new java.awt.Dimension(115, 37));
        btnXem.setMinimumSize(new java.awt.Dimension(115, 37));
        btnXem.setPreferredSize(new java.awt.Dimension(125, 37));
        btnXem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemActionPerformed(evt);
            }
        });

        btnThem.setBackground(new java.awt.Color(204, 255, 204));
        btnThem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interface/Images/them.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.setMargin(new java.awt.Insets(2, 2, 3, 2));
        btnThem.setMaximumSize(new java.awt.Dimension(115, 37));
        btnThem.setMinimumSize(new java.awt.Dimension(115, 37));
        btnThem.setPreferredSize(new java.awt.Dimension(125, 37));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnXoaPhanDoan.setBackground(new java.awt.Color(204, 255, 204));
        btnXoaPhanDoan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXoaPhanDoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interface/Images/icon-ThoiViec.png"))); // NOI18N
        btnXoaPhanDoan.setText("Xóa");
        btnXoaPhanDoan.setMargin(new java.awt.Insets(2, 2, 3, 2));
        btnXoaPhanDoan.setPreferredSize(new java.awt.Dimension(125, 23));
        btnXoaPhanDoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaPhanDoanActionPerformed(evt);
            }
        });

        btnLamMoi.setBackground(new java.awt.Color(204, 255, 204));
        btnLamMoi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interface/Images/icon-LamMoi.png"))); // NOI18N
        btnLamMoi.setText("Làm mới");
        btnLamMoi.setMargin(new java.awt.Insets(2, 2, 3, 2));
        btnLamMoi.setPreferredSize(new java.awt.Dimension(125, 22));
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnXuatExcel.setBackground(new java.awt.Color(204, 255, 204));
        btnXuatExcel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXuatExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interface/Images/icon-XuatExcel.png"))); // NOI18N
        btnXuatExcel.setText("Xuất Excel");
        btnXuatExcel.setMargin(new java.awt.Insets(2, 0, 3, 0));
        btnXuatExcel.setPreferredSize(new java.awt.Dimension(125, 22));

        btnThoat.setBackground(new java.awt.Color(204, 255, 204));
        btnThoat.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interface/Images/icon-thoat.png"))); // NOI18N
        btnThoat.setText("Thoát");
        btnThoat.setMargin(new java.awt.Insets(2, 2, 3, 2));
        btnThoat.setPreferredSize(new java.awt.Dimension(125, 23));
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlNutChucNangLayout = new javax.swing.GroupLayout(pnlNutChucNang);
        pnlNutChucNang.setLayout(pnlNutChucNangLayout);
        pnlNutChucNangLayout.setHorizontalGroup(
            pnlNutChucNangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNutChucNangLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(btnXoaPhanDoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(btnXuatExcel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlNutChucNangLayout.setVerticalGroup(
            pnlNutChucNangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNutChucNangLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(pnlNutChucNangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaPhanDoan, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXuatExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách phân đoạn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        tblDanhSachPhanDoan.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDanhSachPhanDoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachPhanDoanMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblDanhSachPhanDoan);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(105, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlNutChucNang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(630, 630, 630)
                .addComponent(lblTieuDe1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(lblTieuDe1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(pnlNutChucNang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(156, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnXemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemActionPerformed
        // TODO add your handling code here:
        xemPhanDoan();
    }//GEN-LAST:event_btnXemActionPerformed

    private void txtMaPhanDoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaPhanDoanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaPhanDoanActionPerformed

    private void txtGiaPhanDoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaPhanDoanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaPhanDoanActionPerformed

    private void txtTenPhanDoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenPhanDoanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenPhanDoanActionPerformed

    private void tblDanhSachSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachSanPhamMouseClicked
        // TODO add your handling code here:
        int row = tblDanhSachSanPham.getSelectedRow();
        if (row != -1) {
            txtMaSanPham.setText(tblDanhSachSanPham.getValueAt(row, 0).toString());
            txtTenSanPham.setText(tblDanhSachSanPham.getValueAt(row, 1).toString());
        }
    }//GEN-LAST:event_tblDanhSachSanPhamMouseClicked

    private void tblDanhSachPhanDoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachPhanDoanMouseClicked
        // TODO add your handling code here:
        int row = tblDanhSachPhanDoan.getSelectedRow();
        if (row != -1) {
            txtMaPhanDoan.setText(tblDanhSachPhanDoan.getValueAt(row, 0).toString());
            txtTenPhanDoan.setText(tblDanhSachPhanDoan.getValueAt(row, 1).toString());
            txtMaSanPham.setText(tblDanhSachPhanDoan.getValueAt(row, 2).toString());
            txtTenSanPham.setText(tblDanhSachPhanDoan.getValueAt(row, 3).toString());
            txtGiaPhanDoan.setText(tblDanhSachPhanDoan.getValueAt(row, 4).toString());
            jComboBoxPhanDoanYeuCau.setSelectedItem(tblDanhSachPhanDoan.getValueAt(row, 5));

        }

    }//GEN-LAST:event_tblDanhSachPhanDoanMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
// TODO add your handling code here:
        String maPhanDoan = txtMaPhanDoan.getText();
        String tenPhanDoan = txtTenPhanDoan.getText();
        String maSanPham = txtMaSanPham.getText();
        String giaPhanDoanText = txtGiaPhanDoan.getText();
        String phanDoanYeuCau = (String) jComboBoxPhanDoanYeuCau.getSelectedItem();

        // Kiểm tra dữ liệu đã nhập đầy đủ hay chưa
        if (maPhanDoan.isEmpty() || tenPhanDoan.isEmpty() || maSanPham.isEmpty() || giaPhanDoanText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin phân đoạn");
            return;
        }

        // Chuyển đổi giá phân đoạn sang kiểu double
        double giaPhanDoan = 0;
        try {
            giaPhanDoan = Double.parseDouble(giaPhanDoanText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Giá phân đoạn không hợp lệ");
            return;
        }
        SanPham sanPham = new SanPham();
        sanPham.setMaSanPham(maSanPham);
        // Thêm phân đoạn vào CSDL
        PhanDoan pd = new PhanDoan(maPhanDoan, tenPhanDoan, giaPhanDoan, phanDoanYeuCau, sanPham);
        if (phanDoanDao.themPhanDoan(pd)) {
            JOptionPane.showMessageDialog(this, "Thêm phân đoạn thành công");
            xemPhanDoan();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm phân đoạn thất bại");
            xoaDuLieuTrongBang();
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnXoaPhanDoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaPhanDoanActionPerformed
        // TODO add your handling code here:
        int row = tblDanhSachPhanDoan.getSelectedRow();
        if (row != -1) {
            String maPhanDoan = tblDanhSachPhanDoan.getValueAt(row, 0).toString();

            if (phanDoanDao.xoaPhanDoan(maPhanDoan)) {
                JOptionPane.showMessageDialog(this, "Xóa phân đoạn thành công");
                xemPhanDoan();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa phân đoạn không thành công");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phân đoạn để xóa");
        }
    }//GEN-LAST:event_btnXoaPhanDoanActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        // TODO add your handling code here:
        SwingUtilities.getWindowAncestor(this).dispose();
        this.setVisible(false);
        new TrangChu_GUI().setVisible(true);
    }//GEN-LAST:event_btnThoatActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        xoaRong();
        xoaDuLieuTrongBang();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void txtMaPhanDoanFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaPhanDoanFocusLost
        // TODO add your handling code here:
        String maPhanDoan = txtMaPhanDoan.getText();
        String regex = "^PD-[0-9]{5}[1-9]$";
        if (!maPhanDoan.matches(regex)) {
            JOptionPane.showMessageDialog(this, "Mã phân đoạn không đúng định dạng (PD-000001)");
            txtMaPhanDoan.requestFocus();
        }
    }//GEN-LAST:event_txtMaPhanDoanFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThoat;
    private javax.swing.JButton btnXem;
    private javax.swing.JButton btnXoaPhanDoan;
    private javax.swing.JButton btnXuatExcel;
    private javax.swing.JComboBox<String> jComboBoxPhanDoanYeuCau;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblMaSanPham;
    private javax.swing.JLabel lblMaSanPham1;
    private javax.swing.JLabel lblMaSanPham2;
    private javax.swing.JLabel lblMaSanPham3;
    private javax.swing.JLabel lblMaSanPham4;
    private javax.swing.JLabel lblMaSanPham5;
    private javax.swing.JLabel lblTieuDe1;
    private javax.swing.JPanel pnlNutChucNang;
    private javax.swing.JTable tblDanhSachPhanDoan;
    private javax.swing.JTable tblDanhSachSanPham;
    private javax.swing.JTextField txtGiaPhanDoan;
    private javax.swing.JTextField txtMaPhanDoan;
    private javax.swing.JTextField txtMaSanPham;
    private javax.swing.JTextField txtTenPhanDoan;
    private javax.swing.JTextField txtTenSanPham;
    // End of variables declaration//GEN-END:variables
}
