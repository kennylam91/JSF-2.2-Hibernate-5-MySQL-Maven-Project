package com.data;

import java.util.Date;
import java.util.Random;

import com.beans.Student;
import com.beans.Subject;
import com.beans.User;
import com.constant.AUTHORITIES;
import com.constant.FIELDS;
import com.constant.GENDERS;
import com.repository.StudentRepository;
import com.repository.SubjectRepository;
import com.repository.UserRepository;
import com.repository.impl.StudentRepositoryImpl;
import com.repository.impl.SubjectRepositoryImpl;
import com.repository.impl.UserRepositoryImpl;

public class GenerateData {

	private static Random random = new Random();

	private static final String UPPER_ALPHABET_NUMBER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final String UPPER_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String LOWER_ALPHABET = "abcdefghijklmnopqrstuvwxyz";
	private static final String NUMBER = "0123456789";
	private static final String[] LAST_NAME_COLLECTION = { "Lam", "Thang", "Dung", "Ngoc", "Nga", "Huyen", "Thao", "An",
			"Anh", "Luyen", "Cham", "Dung", "Duong", "Duc", "Van", "Hiep", "Khanh", "Tuan", "Nam", "Nhat", "Minh",
			"Lien", "Lung", "Lanh", "Phuong", "Thao", "Thuong", "Thom", "Quynh", "Son", "Toan" };
	private static final String[] FIRST_NAME_COLLECTION = { "Nguyen", "Pham", "Do", "Hoang", "Le", "Dinh", "Duong",
			"Phung", "Ly", "Phan", "Huynh" };
	private static final FIELDS[] FIELD_COLLECTION = { FIELDS.JAVA, FIELDS.PHP, FIELDS.PYTHON };
	private static final String[] ADDRESS_COLLECTION = { "Ha Noi", "Hai Phong", "Ho Chi Minh", "Da Nang", "Hue",
			"Can Tho", "Quang Ninh" };
	private static final GENDERS[] GENDER_COLLECTION = { GENDERS.MALE, GENDERS.FEMALE, GENDERS.OTHER, GENDERS.MALE,
			GENDERS.FEMALE, GENDERS.MALE, GENDERS.FEMALE, GENDERS.MALE, GENDERS.FEMALE, GENDERS.MALE, GENDERS.FEMALE,
			GENDERS.MALE, GENDERS.FEMALE };

	private static final float[] COEFFICIENT_COLLECTION = { 2.0f, 3.0f, 4.0f, 5.0f };

	private static final String[] SUBJECT_NAME_COLLECTION = { "Java Web Basic", "PHP Web Basic", "Java Core",
			"PHP Corec", "Python Web Basic", "Java Immediate", "PHP Immediate", "Python Immediate", "Java Advanced",
			"PHP Advanced", "Python Basic", "Python advanced" };

	private static final FIELDS[] SUBJECT_FIELD_COLLECTION = { FIELDS.JAVA, FIELDS.PHP, FIELDS.JAVA, FIELDS.PHP,
			FIELDS.PYTHON, FIELDS.JAVA, FIELDS.PHP, FIELDS.PYTHON, FIELDS.JAVA, FIELDS.PHP, FIELDS.PYTHON,
			FIELDS.PYTHON };

	public static void main(String[] args) {

		/*
		 * Course course = new Course(); course.setCode("Alkf29");
		 * course.setName("Java Core 01"); course.setStatus("Registering");
		 * course.setBeginTime(getRandomDOB());
		 * 
		 * CourseRepository courseRepo = new CourseRepositoryImpl();
		 * courseRepo.saveCourse(course);
		 */

//		insertSubjectSQL(10);

		insertStudentAndUserSQL(4000);

		/*
		 * SubjectController subjectController = new SubjectController(); List<Subject>
		 * subjects = subjectController.getSubjects();
		 */

		/*
		 * SubjectService subjectService = new SubjectServiceImpl(); List<Subject>
		 * subjects = subjectService.findAllSubjects();
		 */

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

	private static FIELDS getRandomField() {
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
		email.append(firstName.toLowerCase()).append(".").append(lastName.toLowerCase())
				.append(String.valueOf(getRandomNumberBetween(0, 10000))).append("@gmail.com");
		return email.toString();
	}

	private static GENDERS getRandomGender() {
		return GENDER_COLLECTION[getRandomNumberBetween(0, GENDER_COLLECTION.length - 1)];
	}

	private static void insertStudentAndUserSQL(int studentNumber) {
		Student student = new Student();
		StudentRepository repo = new StudentRepositoryImpl();
		UserRepository userRepo = new UserRepositoryImpl();
		User user = new User();
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
			String email = getRandomEmail(firstName, lastName);
			student.setEmail(email);
			user.setEmail(email);
			user.setUsername(firstName + " " + lastName);
			user.setPassword((firstName + lastName).toLowerCase());
			user.setAuthority(AUTHORITIES.STUDENT_ROLE);
			repo.saveStudent(student);
			userRepo.save(user);
		}
	}

	private static void insertUserSQL(int number) {

	}

	private static void insertSubjectSQL(int subjectNumber) {
		Subject subject = new Subject();
		SubjectRepository subjectRepo = new SubjectRepositoryImpl();
		for (int i = 0; i < subjectNumber; i++) {
			subject.setCode(getRandomCode(4));
			subject.setCoefficient(getRandomCoefficient());
			subject.setName(SUBJECT_NAME_COLLECTION[i]);
			subject.setField(SUBJECT_FIELD_COLLECTION[i]);
			subjectRepo.saveSubject(subject);
		}

	}

	private static float getRandomCoefficient() {
		return COEFFICIENT_COLLECTION[getRandomNumberBetween(0, COEFFICIENT_COLLECTION.length - 1)];
	}

	private static String getRandomSubjectName() {
		return SUBJECT_NAME_COLLECTION[getRandomNumberBetween(0, SUBJECT_NAME_COLLECTION.length - 1)];
	}
}
