package com.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.Bean.Student;
import com.repository.StudentRepository;
import com.repository.StudentRepositoryImpl;

public class GenerateData {

	private static Random random = new Random();

	private static final String UPPER_ALPHABET_NUMBER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final String UPPER_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String LOWER_ALPHABET = "abcdefghijklmnopqrstuvwxyz";
	private static final String NUMBER = "0123456789";
	private static final String[] LAST_NAME_COLLECTION = { "Lam", "Thang", "Dung", "Ngoc", "Nga", "Huyen", "Thao", "An",
			"Anh", "Luyen", "Cham", "Dung", "Duong", "Duc", "Van", "Hiep", "Khanh", "Tuan", "Nam", "Nhat", "Minh" };
	private static final String[] FIRST_NAME_COLLECTION = { "Nguyen", "Pham", "Do", "Hoang", "Le", "Dinh", "Duong" };
	private static final String[] FIELD_COLLECTION = { "JAVA", "PHP", "PYTHON" };
	private static final String[] ADDRESS_COLLECTION = { "Ha Noi", "Hai Phong", "Ho Chi Minh", "Da Nang", "Hue",
			"Can Tho", "Quang Ninh" };

	public static void main(String[] args) {
		/*
		 * System.out.println("auto generate student:"); System.out.println("code: " +
		 * getRandomStudentCode(8) + "\n" + "firstname: " + getRandomStudentFirstname()
		 * + "\n" + "lastname: " + getRandomStudentLastname() + "\n" + "dob: " +
		 * getRandomDOB() + "\n" + "field: " + getRandomField() + "\n" + "phone: " +
		 * getRandomPhone() + "\n" + "email: " + getRandomEmail());
		 */
		insertStudentSQL(1000);

	}

	private static String getRandomStudentFirstname() {
		return FIRST_NAME_COLLECTION[getRandomNumberBetween(0, FIRST_NAME_COLLECTION.length - 1)];
	}

	private static String getRandomStudentLastname() {
		return LAST_NAME_COLLECTION[getRandomNumberBetween(0, LAST_NAME_COLLECTION.length - 1)];
	}

	private static Date getRandomDOB() {
		int day = getRandomNumberBetween(1, 29);
		int month = getRandomNumberBetween(1, 12);
		int year = getRandomNumberBetween(1990, 2000);
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.set(year, month, day);
		/*
		 * SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		 * return format1.format(calendar.getTime());
		 */
		return calendar.getTime();
	}

	private static String getRandomStudentCode(int length) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int j = getRandomNumberBetween(0, 35);
			stringBuilder.append(UPPER_ALPHABET_NUMBER.charAt(j));
		}
		return stringBuilder.toString();

	}

	private static int getRandomNumberBetween(int min, int max) {
		return random.nextInt(max - min + 1) + min;
	}

	private static String getRandomField() {
		return FIELD_COLLECTION[getRandomNumberBetween(0, FIELD_COLLECTION.length - 1)];
	}

	private static String getRandomAddress() {
		return ADDRESS_COLLECTION[getRandomNumberBetween(0, ADDRESS_COLLECTION.length - 1)];
	}

	private static String getRandomPhone() {
		StringBuilder phone = new StringBuilder("0");
		for (int i = 0; i < 10; i++) {
			phone.append(NUMBER.charAt(getRandomNumberBetween(0, 9)));
		}
		return phone.toString();
	}

	private static String getRandomEmail(String firstName, String lastName) {
		StringBuilder email = new StringBuilder();
		email.append(firstName).append(".").append(lastName).append("@gmail.com");
		return email.toString();
	}

	private static void insertStudentSQL(int studentNumber) {
		Student student = new Student();
		StudentRepository repo = new StudentRepositoryImpl();
		for (int i = 0; i < studentNumber; i++) {
			String firstName = getRandomStudentFirstname();
			String lastName = getRandomStudentLastname();
			student.setFirstName(firstName);
			student.setAddress(getRandomAddress());
			student.setCode(getRandomStudentCode(8));
			student.setLastName(lastName);
			student.setField(getRandomField());
			student.setDOB(getRandomDOB());
			student.setPhone(getRandomPhone());
			student.setEmail(getRandomEmail(firstName, lastName));
			repo.saveStudent(student);
		}

		/*
		 * StringBuilder finalSql = new StringBuilder(); for (int i = 0; i <
		 * studentNumber; i++) { StringBuilder sql = new StringBuilder(
		 * "INSERT INTO `test`.`students` (`dob`, `address`, `avg_score`, `code`, `email`, `field`, `first_name`, `last_name`, `phone`) VALUES ("
		 * ); sql.append("'").append(getRandomDOB()).append("',").append("'").append(
		 * getRandomAddress()).append("',")
		 * .append("'").append(String.valueOf(random.nextFloat() *
		 * 10)).append("',").append("'")
		 * .append(getRandomStudentCode(8)).append("',").append("'").append(
		 * getRandomEmail()).append("',")
		 * .append("'").append(getRandomField()).append("',").append("'").append(
		 * getRandomStudentFirstname())
		 * .append("',").append("'").append(getRandomStudentLastname()).append("',").
		 * append("'") .append(getRandomPhone()).append("');");
		 * 
		 * finalSql.append(sql); }
		 * 
		 * return finalSql.toString();
		 */
	}
}
