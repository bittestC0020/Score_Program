import java.util.*;
import java.io.*;

//�л��� �⺻���� ������ ��ӹ޴� Student_info�� ��ӹް� Student������ ������ ����, ����, ����, ����� ��
public class Student extends Student_info{
	int java;//����1 �ڹ�
	char java_grade;
	int C;//����2 C���
	char C_grade;
	int python;//����3 ���̽�
	char python_grade;
	int sum;
	double avg;
	public Student(String name, int std_id, String dept, String tel) {
		super(name, std_id, dept, tel);
		java=C=python=sum=-1;
		avg=-1.0;
		java_grade=C_grade=python_grade='n';
	}

	public Student(String name, int std_id, String dept, String tel, int java, int c, int python) {
		super(name, std_id, dept, tel);
		this.java = java;
		C = c;
		this.python = python;
		sum=java+c+python;
		avg=(java+c+python)/3;
		switch(java/10) {
		case 10:
		case 9:
			java_grade='A';
			break;
		case 8:
			java_grade='B';
			break;
		case 7:
			java_grade='C';
			break;
		case 6:
			java_grade='D';
			break;
		default:
			java_grade='F';
		}
		switch(c/10) {
		case 10:
		case 9:
			C_grade='A';
			break;
		case 8:
			C_grade='B';
			break;
		case 7:
			C_grade='C';
			break;
		case 6:
			C_grade='D';
			break;
		default:
			C_grade='F';
		}
		switch(python/10) {
		case 10:
		case 9:
			python_grade='A';
			break;
		case 8:
			python_grade='B';
			break;
		case 7:
			python_grade='C';
			break;
		case 6:
			python_grade='D';
			break;
		default:
			python_grade='F';
		}
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(int java, int C, int python) {
		avg = (java+C+python)/3;
	}
	
	public int getJava() {
		return java;
	}
	public void setJava(int java) {
		this.java = java;
	}
	public char getJava_grade() {
		return java_grade;
	}
	//90�� �̻� 'A', 80���̻� 'B', 70���̻� 'C', 60���̻� 'D', ������ 'F'
	public void setJava_grade(int java) {
		switch(java/10) {
		case 10:
		case 9:
			java_grade='A';
			break;
		case 8:
			java_grade='B';
			break;
		case 7:
			java_grade='C';
			break;
		case 6:
			java_grade='D';
			break;
		default:
			java_grade='F';
		}
	}
	public int getC() {
		return C;
	}
	public void setC(int c) {
		C = c;
	}
	public char getC_grade() {
		return C_grade;
	}
	public void setC_grade(int c) {
		switch(c/10) {
		case 10:
		case 9:
			C_grade='A';
			break;
		case 8:
			C_grade='B';
			break;
		case 7:
			C_grade='C';
			break;
		case 6:
			C_grade='D';
			break;
		default:
			C_grade='F';
		}
	}
	public int getPython() {
		return python;
	}
	public void setPython(int python) {
		this.python = python;
	}
	public char getPython_grade() {
		return python_grade;
	}
	public void setPython_grade(int python) {
		switch(python/10) {
		case 10:
		case 9:
			python_grade='A';
			break;
		case 8:
			python_grade='B';
			break;
		case 7:
			python_grade='C';
			break;
		case 6:
			python_grade='D';
			break;
		default:
			python_grade='F';
		}
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int java, int C, int python) {
		sum = java+C+python;
	}
}

//�л� ��ü�� �ٷ�� Ŭ����
class AllStudent implements Aggregate{
	private Vector students;
	public AllStudent(int n) {
		this.students=new Vector(n);
	}
	//�ش� �л��� ������.
	public Student getStudent(int index) {
		return (Student)students.get(index);
	}
	//�л� �߰�
	public void appendStudent(Student student) {
		students.add(student);
	}
	//��ü �л�����
	public void removeAll() {
		students.removeAllElements();
	}
	//��ü �л��� ��
	public int getLength() {
		return students.size();
	}
	public Iterator iterator() {
		return new StudentIterator(this);
	}
}

//���� �л��� ������ ���� ���� Ŭ����
class StudentIterator implements Iterator{
	private AllStudent stu_all;
	private int index;

	public StudentIterator(AllStudent stu_all) {
		this.stu_all = stu_all;
		this.index = 0;
	}

	@Override
	public boolean hasNext() {
		if (index<stu_all.getLength()) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Object next() {
		Student ss=stu_all.getStudent(index);
		index++;
		return ss;
	}

}

