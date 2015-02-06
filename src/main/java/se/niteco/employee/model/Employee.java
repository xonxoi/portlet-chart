package se.niteco.employee.model;

public class Employee {

		private String employeeId;
		private String employeeName;
		private String department;
		private String position;
		private Integer salary;
		private String picture;
		private String address;
		
		/**
		 * /home/xonmai/Desktop/no-avatar.jpg
		 */
		public Employee(){
			this.employeeId = null;
			this.employeeName = null;
			this.department = null;
			this.position = null;
			this.salary = 0;
			this.picture = null;
		}
		/**
		 * @param id
		 * @param name
		 * @param dept
		 * @param pos
		 * @param sal
		 */
		public Employee(String id, String name, String dept, String pos, Integer sal){
			this.employeeId = id;
			this.employeeName = name;
			this.department = dept;
			this.position = pos;
			this.salary = sal;
		}
		/**
		 * @return the employeeId
		 */
		public String getEmployeeId() {
			return employeeId;
		}
		/**
		 * @param employeeId the employeeId to set
		 */
		public void setEmployeeId(String employeeId) {
			this.employeeId = employeeId;
		}
		/**
		 * @return the employeeName
		 */
		public String getEmployeeName() {
			return employeeName;
		}
		/**
		 * @param employeeName the employeeName to set
		 */
		public void setEmployeeName(String employeeName) {
			this.employeeName = employeeName;
		}
		/**
		 * @return the department
		 */
		public String getDepartment() {
			return department;
		}
		/**
		 * @param department the department to set
		 */
		public void setDepartment(String department) {
			this.department = department;
		}
		/**
		 * @return the position
		 */
		public String getPosition() {
			return position;
		}
		/**
		 * @param position the position to set
		 */
		public void setPosition(String position) {
			this.position = position;
		}
		/**
		 * @return the salary
		 */
		public Integer getSalary() {
			return salary;
		}
		/**
		 * @param salary the salary to set
		 */
		public void setSalary(Integer salary) {
			this.salary = salary;
		}
		/**
		 * @return the picture
		 */
		public String getPicture() {
			return picture;
		}
		/**
		 * @param picture the picture to set
		 */
		public void setPicture(String picture) {
			this.picture = picture;
		}
		/**
		 * @return the address
		 */
		public String getAddress() {
			return address;
		}
		/**
		 * @param address the address to set
		 */
		public void setAddress(String address) {
			this.address = address;
		}
		
		
}
