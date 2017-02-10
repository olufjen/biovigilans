package no.naks.web.framework;

import java.io.PrintWriter;

import org.apache.log4j.Category;
import org.apache.log4j.Priority;

public class Log4jPrintWriter extends PrintWriter {
	Priority level;
	Category cat;
	StringBuffer text = new StringBuffer("");

	public Log4jPrintWriter(org.apache.log4j.Category cat,
			org.apache.log4j.Priority level) {
		super(System.err); 
		this.level = level;
		this.cat = cat;
	}

	public void close() {
		flush();
	}

	public void flush() {
		if (!text.toString().equals("")) {
			cat.log(level, text.toString());
			text.setLength(0);
		}
	}

	public void print(boolean b) {
		text.append(b);
	}

	public void print(char c) {
		text.append(c);
	}

	public void print(char[] s) {
		text.append(s);
	}

	public void print(double d) {
		text.append(d);
	}

	public void print(float f) {
		text.append(f);
	}

	public void print(int i) {
		text.append(i);
	}

	public void print(long l) {
		text.append(l);
	}

	public void print(Object obj) {
		text.append(obj);
	}

	public void print(String s) {
		text.append(s);
	}

	public void println() {
		if (!text.toString().equals("")) {
			cat.log(level, text.toString());
			text.setLength(0);
		}
	}

	public void println(boolean x) {
		text.append(x);
		cat.log(level, text.toString());
		text.setLength(0);
	}

	public void println(char x) {
		text.append(x);
		cat.log(level, text.toString());
		text.setLength(0);
	}

	public void println(char[] x) {
		text.append(x);
		cat.log(level, text.toString());
		text.setLength(0);
	}

	public void println(double x) {
		text.append(x);
		cat.log(level, text.toString());
		text.setLength(0);
	}

	public void println(float x) {
		text.append(x);
		cat.log(level, text.toString());
		text.setLength(0);
	}

	public void println(int x) {
		text.append(x);
		cat.log(level, text.toString());
		text.setLength(0);
	}

	public void println(long x) {
		text.append(x);
		cat.log(level, text.toString());
		text.setLength(0);
	}

	public void println(Object x) {
		text.append(x);
		cat.log(level, text.toString());
		text.setLength(0);
	}

	public void println(String x) {
		text.append(x);
		cat.log(level, text.toString());
		text.setLength(0);
	}
}
