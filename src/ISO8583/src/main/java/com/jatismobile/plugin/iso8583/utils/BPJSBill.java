package com.jatismobile.plugin.iso8583.utils;

public class BPJSBill {
	public static String getBill(String noReferensi, String tanggalLunas, String noVaKeluarga, String noVaKepalaKeluarga, String nama, String jumlahAnggotaKeluarga, String periode, String jumlahTagihan, String biayaAdmin) {
		String output = "";
		String suffix = "</td></tr>";
        String tbl = "<table class=\"responsive-table\">";
        tbl += "<tr><td colspan=\"3\"><strong>INFORMASI TEKNOLOGI INDONESIA - JTSMOBILE</strong></td></tr>";
        tbl += "<tr><td colspan=\"3\"></td></tr>";
        tbl += "<tr><td colspan=\"3\"><strong><center>STRUK PEMBAYARAN IURAN BPJS KESEHATAN</center></strong></td></tr>";
        tbl += "<tr><td><b>NO REFERENSI</b></td><td>:</td><td>" + noReferensi + suffix;
        tbl += "<tr><td><b>TANGGAL</b></td><td>:</td><td>" + tanggalLunas + suffix;
        tbl += "<tr><td><b>NO. VA KELUARGA</b></td><td>:</td><td>" + noVaKeluarga + suffix;
        tbl += "<tr><td><b>NO. VA KEPALA KELUARGA</b></td><td>:</td><td>" + noVaKepalaKeluarga + suffix;
        tbl += "<tr><td><b>NAMA PESERTA</b></td><td>:</td><td>" + nama + suffix;
        tbl += "<tr><td><b>JML ANGG KELUARGA</b></td><td>:</td><td>" + jumlahAnggotaKeluarga + suffix;
        tbl += "<tr><td><b>PERIODE</b></td><td>:</td><td>" + periode + suffix;
        tbl += "<tr><td><b>JUMLAH TAGIHAN</b></td><td>:</td><td> RP." + jumlahTagihan + suffix;
        tbl += "<tr><td><b>BIAYA ADMIN</b></td><td>:</td><td> RP." + biayaAdmin + suffix;
        tbl += "<tr><td colspan=\"3\"></td></tr>";
        tbl += "<tr><td colspan=\"3\"><b>BPJS KESEHATAN MENYATAKAN STRUK INI SEBAGAI BUKTI PEMBAYARAN YANG SAH</b></td></tr>";
        tbl += "</table>";
        
        output = tbl;
		return output;
	}
}
