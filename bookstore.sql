USE [Bookstore]
GO
/****** Object:  User [bookstore_user]    Script Date: 5/7/2025 11:49:54 AM ******/
CREATE USER [bookstore_user] FOR LOGIN [bookstore_user] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_owner] ADD MEMBER [bookstore_user]
GO
/****** Object:  Table [dbo].[chi_tiet_don_hang]    Script Date: 5/7/2025 11:49:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[chi_tiet_don_hang](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[don_hang_id] [int] NOT NULL,
	[tac_pham_id] [int] NOT NULL,
	[so_luong] [int] NOT NULL,
	[gia_ban] [decimal](10, 2) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[danh_gia]    Script Date: 5/7/2025 11:49:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[danh_gia](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[khach_hang_id] [int] NOT NULL,
	[tac_pham_id] [int] NOT NULL,
	[diem_danh_gia] [int] NOT NULL,
	[noi_dung] [nvarchar](128) NOT NULL,
	[ngay_danh_gia] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[dia_chi]    Script Date: 5/7/2025 11:49:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[dia_chi](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[khach_hang_id] [int] NOT NULL,
	[ho_ten] [nvarchar](45) NOT NULL,
	[so_dien_thoai] [nvarchar](15) NOT NULL,
	[dia_chi] [nvarchar](64) NOT NULL,
	[tinh_thanh] [nvarchar](45) NOT NULL,
	[quan_huyen] [nvarchar](45) NOT NULL,
	[phuong_xa] [nvarchar](45) NOT NULL,
	[ma_buu_dien] [nvarchar](10) NULL,
	[mac_dinh] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[don_hang]    Script Date: 5/7/2025 11:49:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[don_hang](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[khach_hang_id] [int] NOT NULL,
	[ngay_dat_hang] [datetime] NOT NULL,
	[ngay_giao_hang] [datetime] NULL,
	[ngay_nhan_hang] [datetime] NULL,
	[trang_thai] [nvarchar](45) NOT NULL,
	[tong_tien] [decimal](10, 2) NOT NULL,
	[tong_thanh_toan] [decimal](10, 2) NOT NULL,
	[ghi_chu] [nvarchar](256) NULL,
	[ho_ten] [nvarchar](45) NOT NULL,
	[so_dien_thoai] [nvarchar](15) NOT NULL,
	[dia_chi] [nvarchar](64) NOT NULL,
	[tinh_thanh] [nvarchar](45) NOT NULL,
	[quan_huyen] [nvarchar](45) NOT NULL,
	[phuong_xa] [nvarchar](45) NOT NULL,
	[ma_buu_dien] [nvarchar](10) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[gio_hang_item]    Script Date: 5/7/2025 11:49:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[gio_hang_item](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[khach_hang_id] [int] NOT NULL,
	[tac_pham_id] [int] NOT NULL,
	[so_luong] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[hinh_anh_sach]    Script Date: 5/7/2025 11:49:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[hinh_anh_sach](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[tac_pham_id] [int] NOT NULL,
	[ten_hinh_anh] [nvarchar](256) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[khach_hang]    Script Date: 5/7/2025 11:49:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[khach_hang](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[email] [nvarchar](128) NOT NULL,
	[mat_khau] [nvarchar](64) NOT NULL,
	[ho_ten] [nvarchar](45) NOT NULL,
	[so_dien_thoai] [nvarchar](15) NOT NULL,
	[ngay_tao] [datetime] NOT NULL,
	[da_kich_hoat] [bit] NOT NULL,
	[loai_xac_thuc] [nvarchar](20) NOT NULL,
	[ma_xac_thuc] [nvarchar](64) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[nguoi_dung]    Script Date: 5/7/2025 11:49:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[nguoi_dung](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[email] [nvarchar](128) NOT NULL,
	[mat_khau] [nvarchar](64) NOT NULL,
	[ho_ten] [nvarchar](45) NOT NULL,
	[so_dien_thoai] [nvarchar](15) NOT NULL,
	[hinh_anh] [nvarchar](64) NULL,
	[da_kich_hoat] [bit] NOT NULL,
	[loai_xac_thuc] [nvarchar](20) NOT NULL,
	[ma_xac_thuc] [nvarchar](64) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[nha_xuat_ban]    Script Date: 5/7/2025 11:49:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[nha_xuat_ban](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten_nha_xuat_ban] [nvarchar](128) NOT NULL,
	[duong_dan] [nvarchar](128) NOT NULL,
	[hinh_anh] [nvarchar](128) NULL,
	[da_hien_thi] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[ten_nha_xuat_ban] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[duong_dan] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[sach_yeu_thich]    Script Date: 5/7/2025 11:49:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[sach_yeu_thich](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[khach_hang_id] [int] NOT NULL,
	[tac_pham_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tac_pham]    Script Date: 5/7/2025 11:49:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tac_pham](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[tua_de] [nvarchar](256) NOT NULL,
	[duong_dan] [nvarchar](256) NOT NULL,
	[tom_tat] [nvarchar](max) NOT NULL,
	[noi_dung] [nvarchar](max) NOT NULL,
	[ngay_xuat_ban] [date] NULL,
	[ngay_cap_nhat] [datetime] NULL,
	[da_xuat_ban] [bit] NOT NULL,
	[so_luong_ton] [int] NULL,
	[gia_nhap] [decimal](10, 2) NULL,
	[gia_ban] [decimal](10, 2) NULL,
	[phan_tram_giam_gia] [decimal](5, 2) NULL,
	[anh_bia] [nvarchar](256) NOT NULL,
	[review_count] [int] NULL,
	[average_rating] [decimal](3, 2) NULL,
	[length_cm] [decimal](5, 2) NULL,
	[width_cm] [decimal](5, 2) NULL,
	[height_cm] [decimal](5, 2) NULL,
	[weight_kg] [decimal](5, 2) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[duong_dan] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[tua_de] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[theo_doi_don_hang]    Script Date: 5/7/2025 11:49:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[theo_doi_don_hang](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[don_hang_id] [int] NOT NULL,
	[trang_thai] [nvarchar](45) NOT NULL,
	[ngay_cap_nhat] [datetime] NOT NULL,
	[ghi_chu] [nvarchar](256) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[thuoc_tinh_sach]    Script Date: 5/7/2025 11:49:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[thuoc_tinh_sach](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[tac_pham_id] [int] NOT NULL,
	[ten_thuoc_tinh] [nvarchar](128) NOT NULL,
	[gia_tri] [nvarchar](256) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tinh_thanh]    Script Date: 5/7/2025 11:49:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tinh_thanh](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten_tinh] [nvarchar](45) NOT NULL,
	[ma_tinh] [nvarchar](5) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[vai_tro]    Script Date: 5/7/2025 11:49:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[vai_tro](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ten_vai_tro] [nvarchar](40) NOT NULL,
	[mo_ta] [nvarchar](150) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[ten_vai_tro] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[dia_chi] ADD  DEFAULT ((0)) FOR [mac_dinh]
GO
ALTER TABLE [dbo].[khach_hang] ADD  DEFAULT (getdate()) FOR [ngay_tao]
GO
ALTER TABLE [dbo].[khach_hang] ADD  DEFAULT ((0)) FOR [da_kich_hoat]
GO
ALTER TABLE [dbo].[nguoi_dung] ADD  DEFAULT ((0)) FOR [da_kich_hoat]
GO
ALTER TABLE [dbo].[nha_xuat_ban] ADD  DEFAULT ((1)) FOR [da_hien_thi]
GO
ALTER TABLE [dbo].[tac_pham] ADD  DEFAULT ((0)) FOR [da_xuat_ban]
GO
ALTER TABLE [dbo].[tac_pham] ADD  DEFAULT ((0)) FOR [review_count]
GO
ALTER TABLE [dbo].[chi_tiet_don_hang]  WITH CHECK ADD FOREIGN KEY([don_hang_id])
REFERENCES [dbo].[don_hang] ([id])
GO
ALTER TABLE [dbo].[chi_tiet_don_hang]  WITH CHECK ADD FOREIGN KEY([tac_pham_id])
REFERENCES [dbo].[tac_pham] ([id])
GO
ALTER TABLE [dbo].[danh_gia]  WITH CHECK ADD FOREIGN KEY([khach_hang_id])
REFERENCES [dbo].[khach_hang] ([id])
GO
ALTER TABLE [dbo].[danh_gia]  WITH CHECK ADD FOREIGN KEY([tac_pham_id])
REFERENCES [dbo].[tac_pham] ([id])
GO
ALTER TABLE [dbo].[dia_chi]  WITH CHECK ADD FOREIGN KEY([khach_hang_id])
REFERENCES [dbo].[khach_hang] ([id])
GO
ALTER TABLE [dbo].[don_hang]  WITH CHECK ADD FOREIGN KEY([khach_hang_id])
REFERENCES [dbo].[khach_hang] ([id])
GO
ALTER TABLE [dbo].[gio_hang_item]  WITH CHECK ADD FOREIGN KEY([khach_hang_id])
REFERENCES [dbo].[khach_hang] ([id])
GO
ALTER TABLE [dbo].[gio_hang_item]  WITH CHECK ADD FOREIGN KEY([tac_pham_id])
REFERENCES [dbo].[tac_pham] ([id])
GO
ALTER TABLE [dbo].[hinh_anh_sach]  WITH CHECK ADD FOREIGN KEY([tac_pham_id])
REFERENCES [dbo].[tac_pham] ([id])
GO
ALTER TABLE [dbo].[sach_yeu_thich]  WITH CHECK ADD FOREIGN KEY([khach_hang_id])
REFERENCES [dbo].[khach_hang] ([id])
GO
ALTER TABLE [dbo].[sach_yeu_thich]  WITH CHECK ADD FOREIGN KEY([tac_pham_id])
REFERENCES [dbo].[tac_pham] ([id])
GO
ALTER TABLE [dbo].[theo_doi_don_hang]  WITH CHECK ADD FOREIGN KEY([don_hang_id])
REFERENCES [dbo].[don_hang] ([id])
GO
ALTER TABLE [dbo].[thuoc_tinh_sach]  WITH CHECK ADD FOREIGN KEY([tac_pham_id])
REFERENCES [dbo].[tac_pham] ([id])
GO
