package com.atsu.pojo;

import java.util.ArrayList;
import java.util.List;

public class EmployeeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EmployeeExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andEmployeeIdIsNull() {
            addCriterion("employee_id is null");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdIsNotNull() {
            addCriterion("employee_id is not null");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdEqualTo(Integer value) {
            addCriterion("employee_id =", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdNotEqualTo(Integer value) {
            addCriterion("employee_id <>", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdGreaterThan(Integer value) {
            addCriterion("employee_id >", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("employee_id >=", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdLessThan(Integer value) {
            addCriterion("employee_id <", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdLessThanOrEqualTo(Integer value) {
            addCriterion("employee_id <=", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdIn(List<Integer> values) {
            addCriterion("employee_id in", values, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdNotIn(List<Integer> values) {
            addCriterion("employee_id not in", values, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdBetween(Integer value1, Integer value2) {
            addCriterion("employee_id between", value1, value2, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("employee_id not between", value1, value2, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameIsNull() {
            addCriterion("employee_name is null");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameIsNotNull() {
            addCriterion("employee_name is not null");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameEqualTo(String value) {
            addCriterion("employee_name =", value, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameNotEqualTo(String value) {
            addCriterion("employee_name <>", value, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameGreaterThan(String value) {
            addCriterion("employee_name >", value, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameGreaterThanOrEqualTo(String value) {
            addCriterion("employee_name >=", value, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameLessThan(String value) {
            addCriterion("employee_name <", value, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameLessThanOrEqualTo(String value) {
            addCriterion("employee_name <=", value, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameLike(String value) {
            addCriterion("employee_name like", value, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameNotLike(String value) {
            addCriterion("employee_name not like", value, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameIn(List<String> values) {
            addCriterion("employee_name in", values, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameNotIn(List<String> values) {
            addCriterion("employee_name not in", values, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameBetween(String value1, String value2) {
            addCriterion("employee_name between", value1, value2, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameNotBetween(String value1, String value2) {
            addCriterion("employee_name not between", value1, value2, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeGenderIsNull() {
            addCriterion("employee_gender is null");
            return (Criteria) this;
        }

        public Criteria andEmployeeGenderIsNotNull() {
            addCriterion("employee_gender is not null");
            return (Criteria) this;
        }

        public Criteria andEmployeeGenderEqualTo(String value) {
            addCriterion("employee_gender =", value, "employeeGender");
            return (Criteria) this;
        }

        public Criteria andEmployeeGenderNotEqualTo(String value) {
            addCriterion("employee_gender <>", value, "employeeGender");
            return (Criteria) this;
        }

        public Criteria andEmployeeGenderGreaterThan(String value) {
            addCriterion("employee_gender >", value, "employeeGender");
            return (Criteria) this;
        }

        public Criteria andEmployeeGenderGreaterThanOrEqualTo(String value) {
            addCriterion("employee_gender >=", value, "employeeGender");
            return (Criteria) this;
        }

        public Criteria andEmployeeGenderLessThan(String value) {
            addCriterion("employee_gender <", value, "employeeGender");
            return (Criteria) this;
        }

        public Criteria andEmployeeGenderLessThanOrEqualTo(String value) {
            addCriterion("employee_gender <=", value, "employeeGender");
            return (Criteria) this;
        }

        public Criteria andEmployeeGenderLike(String value) {
            addCriterion("employee_gender like", value, "employeeGender");
            return (Criteria) this;
        }

        public Criteria andEmployeeGenderNotLike(String value) {
            addCriterion("employee_gender not like", value, "employeeGender");
            return (Criteria) this;
        }

        public Criteria andEmployeeGenderIn(List<String> values) {
            addCriterion("employee_gender in", values, "employeeGender");
            return (Criteria) this;
        }

        public Criteria andEmployeeGenderNotIn(List<String> values) {
            addCriterion("employee_gender not in", values, "employeeGender");
            return (Criteria) this;
        }

        public Criteria andEmployeeGenderBetween(String value1, String value2) {
            addCriterion("employee_gender between", value1, value2, "employeeGender");
            return (Criteria) this;
        }

        public Criteria andEmployeeGenderNotBetween(String value1, String value2) {
            addCriterion("employee_gender not between", value1, value2, "employeeGender");
            return (Criteria) this;
        }

        public Criteria andEmployeeAgeIsNull() {
            addCriterion("employee_age is null");
            return (Criteria) this;
        }

        public Criteria andEmployeeAgeIsNotNull() {
            addCriterion("employee_age is not null");
            return (Criteria) this;
        }

        public Criteria andEmployeeAgeEqualTo(Integer value) {
            addCriterion("employee_age =", value, "employeeAge");
            return (Criteria) this;
        }

        public Criteria andEmployeeAgeNotEqualTo(Integer value) {
            addCriterion("employee_age <>", value, "employeeAge");
            return (Criteria) this;
        }

        public Criteria andEmployeeAgeGreaterThan(Integer value) {
            addCriterion("employee_age >", value, "employeeAge");
            return (Criteria) this;
        }

        public Criteria andEmployeeAgeGreaterThanOrEqualTo(Integer value) {
            addCriterion("employee_age >=", value, "employeeAge");
            return (Criteria) this;
        }

        public Criteria andEmployeeAgeLessThan(Integer value) {
            addCriterion("employee_age <", value, "employeeAge");
            return (Criteria) this;
        }

        public Criteria andEmployeeAgeLessThanOrEqualTo(Integer value) {
            addCriterion("employee_age <=", value, "employeeAge");
            return (Criteria) this;
        }

        public Criteria andEmployeeAgeIn(List<Integer> values) {
            addCriterion("employee_age in", values, "employeeAge");
            return (Criteria) this;
        }

        public Criteria andEmployeeAgeNotIn(List<Integer> values) {
            addCriterion("employee_age not in", values, "employeeAge");
            return (Criteria) this;
        }

        public Criteria andEmployeeAgeBetween(Integer value1, Integer value2) {
            addCriterion("employee_age between", value1, value2, "employeeAge");
            return (Criteria) this;
        }

        public Criteria andEmployeeAgeNotBetween(Integer value1, Integer value2) {
            addCriterion("employee_age not between", value1, value2, "employeeAge");
            return (Criteria) this;
        }

        public Criteria andEmployeeEmailIsNull() {
            addCriterion("employee_email is null");
            return (Criteria) this;
        }

        public Criteria andEmployeeEmailIsNotNull() {
            addCriterion("employee_email is not null");
            return (Criteria) this;
        }

        public Criteria andEmployeeEmailEqualTo(String value) {
            addCriterion("employee_email =", value, "employeeEmail");
            return (Criteria) this;
        }

        public Criteria andEmployeeEmailNotEqualTo(String value) {
            addCriterion("employee_email <>", value, "employeeEmail");
            return (Criteria) this;
        }

        public Criteria andEmployeeEmailGreaterThan(String value) {
            addCriterion("employee_email >", value, "employeeEmail");
            return (Criteria) this;
        }

        public Criteria andEmployeeEmailGreaterThanOrEqualTo(String value) {
            addCriterion("employee_email >=", value, "employeeEmail");
            return (Criteria) this;
        }

        public Criteria andEmployeeEmailLessThan(String value) {
            addCriterion("employee_email <", value, "employeeEmail");
            return (Criteria) this;
        }

        public Criteria andEmployeeEmailLessThanOrEqualTo(String value) {
            addCriterion("employee_email <=", value, "employeeEmail");
            return (Criteria) this;
        }

        public Criteria andEmployeeEmailLike(String value) {
            addCriterion("employee_email like", value, "employeeEmail");
            return (Criteria) this;
        }

        public Criteria andEmployeeEmailNotLike(String value) {
            addCriterion("employee_email not like", value, "employeeEmail");
            return (Criteria) this;
        }

        public Criteria andEmployeeEmailIn(List<String> values) {
            addCriterion("employee_email in", values, "employeeEmail");
            return (Criteria) this;
        }

        public Criteria andEmployeeEmailNotIn(List<String> values) {
            addCriterion("employee_email not in", values, "employeeEmail");
            return (Criteria) this;
        }

        public Criteria andEmployeeEmailBetween(String value1, String value2) {
            addCriterion("employee_email between", value1, value2, "employeeEmail");
            return (Criteria) this;
        }

        public Criteria andEmployeeEmailNotBetween(String value1, String value2) {
            addCriterion("employee_email not between", value1, value2, "employeeEmail");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdIsNull() {
            addCriterion("department_id is null");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdIsNotNull() {
            addCriterion("department_id is not null");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdEqualTo(Integer value) {
            addCriterion("department_id =", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdNotEqualTo(Integer value) {
            addCriterion("department_id <>", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdGreaterThan(Integer value) {
            addCriterion("department_id >", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("department_id >=", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdLessThan(Integer value) {
            addCriterion("department_id <", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdLessThanOrEqualTo(Integer value) {
            addCriterion("department_id <=", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdIn(List<Integer> values) {
            addCriterion("department_id in", values, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdNotIn(List<Integer> values) {
            addCriterion("department_id not in", values, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdBetween(Integer value1, Integer value2) {
            addCriterion("department_id between", value1, value2, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("department_id not between", value1, value2, "departmentId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}