import java.io.File;
import java.io.IOException;
import java.util.Scanner;

//학생의 이름, 학번, 학과, 전화번호를 담고 있는 클래스
public class Student_info{
	String name="";//이름
	int std_id;//학번
	String dept;//학과
	String tel;//전화번호

	public Student_info(String name, int std_id, String dept, String tel) {
		this.name = name;
		this.std_id = std_id;
		this.dept = dept;
		this.tel = tel;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStd_id() {
		return std_id;
	}
	public void setStd_id(int std_id) {
		this.std_id = std_id;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
}
