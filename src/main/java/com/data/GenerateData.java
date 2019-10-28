package com.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.Bean.Student;
import com.Bean.Subject;
import com.repository.StudentRepository;
import com.repository.SubjectRepository;
import com.repository.impl.StudentRepositoryImpl;
import com.repository.impl.SubjectRepositoryImpl;

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
	private static final String[] GENDER_COLLECTION = { "male", "female", "other", "male", "female", "male", "female",
			"male", "female", "male", "female", "male", "female", "male", "female", "male", "female", "male",
			"female" };

	private static final float[] COEFFICIENT_COLLECTION = { 2.0f, 3.0f, 4.0f, 5.0f };

	private static final String[] SUBJECT_NAME_COLLECTION = { "Basic Programming", "Web Basic", "Java Basic",
			"PHP Basic", "Data Struture Basic", "Algorithm Basic", "Java Advanced", "PHP Advanced", "Python Basic",
			"Python advanced" };

	public static void main(String[] args) {

		insertStudentSQL(50);
		insertSubjectSQL(10);

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

	private static String getRandomCode(int length) {
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

	private static String getRandomGender() {
		return GENDER_COLLECTION[getRandomNumberBetween(0, GENDER_COLLECTION.length - 1)];
	}

	private static void insertStudentSQL(int studentNumber) {
		Student student = new Student();
		StudentRepository repo = new StudentRepositoryImpl();
		for (int i = 0; i < studentNumber; i++) {
			String firstName = getRandomStudentFirstname();
			String lastName = getRandomStudentLastname();
			student.setFirstName(firstName);
			student.setAddress(getRandomAddress());
			student.setCode(getRandomCode(8));
			student.setLastName(lastName);
			student.setGender(getRandomGender());
			student.setField(getRandomField());
			student.setDob(getRandomDOB());
			student.setPhone(getRandomPhone());
			student.setEmail(getRandomEmail(firstName, lastName));
			repo.saveStudent(student);
		}
	}

	private static void insertSubjectSQL(int subjectNumber) {
		Subject subject = new Subject();
		SubjectRepository subjectRepo = new SubjectRepositoryImpl();
		for (int i = 0; i < subjectNumber; i++) {
			subject.setCode(getRandomCode(4));
			subject.setCoefficient(getRandomCoefficient());
			subject.setName(SUBJECT_NAME_COLLECTION[i]);
			System.out.println(subjectRepo.saveSubject(subject));
		}

	}

	private static float getRandomCoefficient() {
		return COEFFICIENT_COLLECTION[getRandomNumberBetween(0, COEFFICIENT_COLLECTION.length - 1)];
	}

	private static String getRandomSubjectName() {
		return SUBJECT_NAME_COLLECTION[getRandomNumberBetween(0, SUBJECT_NAME_COLLECTION.length - 1)];
	}
}
