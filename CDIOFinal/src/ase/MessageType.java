package ase;

public enum MessageType {
	WEIGHT_REPLY,
	TARA_REPLY,
	RM20_A, // LabTech send something
	RM20_C, // Labtech cancelled RM20
	RM20_B,
	ERROR,
	P111_A,
	K_A,
	D_A,
	DW_A,
	SEND, // The [-> button
	ZERO, // The zero button
	TARE, // The Tare button
	EXIT // the exit button

}
