package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CBCurrency {
	private String CcyNm_EN;
	private String CcyNm_UZC;
	private String Diff;
	private double Rate;
	private String Ccy;
	private String CcyNmRU;
	private int id;
	private String CcyNm_UZ;
	private String Code;
	private String Nominal;
	private String Date;
}