package com.green.biz.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderVO {
	private int odseq;
	private int oseq;
	private String id;
	private Timestamp indate;
	private String name;
	private String phone;
	private int bseq;
	private String title;
	private int quantity;
	private int price_rent;
	private int price;
	private String result;
}
