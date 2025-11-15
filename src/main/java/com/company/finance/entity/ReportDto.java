package com.company.finance.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.JmixId;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public class ReportDto {

        private LocalDate from;
        private LocalDate through;
        private BigDecimal totalExpense;
        private BigDecimal totalIncome;
        private List<CategoryGridData> categoriesExpense;

        public ReportDto() {
            // Required by Jackson for deserialization
        }
        /**
         * DTO for a report.
         * @param from
         * @param through
         * @param totalExpense
         * @param totalIncome
         * @param categoriesExpense
         * @param categoriesIncome
         */
        public ReportDto(LocalDate from, LocalDate through, BigDecimal totalExpense, BigDecimal totalIncome, List<CategoryGridData> categoriesExpense, List<CategoryGridData> categoriesIncome) {
            this.from = from;
            this.through = through;
            this.totalExpense = totalExpense;
            this.totalIncome = totalIncome;
            this.categoriesExpense = categoriesExpense;
            this.categoriesIncome = categoriesIncome;
        }


        private List<CategoryGridData> categoriesIncome;

        public LocalDate getFrom() {
            return from;
        }

        public void setFrom(LocalDate from) {
            this.from = from;
        }

        public LocalDate getThrough() {
            return through;
        }

        public void setThrough(LocalDate through) {
            this.through = through;
        }

        public BigDecimal getTotalExpense() {
            return totalExpense;
        }

        public void setTotalExpense(BigDecimal totalExpense) {
            this.totalExpense = totalExpense;
        }

        public BigDecimal getTotalIncome() {
            return totalIncome;
        }

        public void setTotalIncome(BigDecimal totalIncome) {
            this.totalIncome = totalIncome;
        }

        public List<CategoryGridData> getCategoriesExpense() {
            return categoriesExpense;
        }

        public void setCategoriesExpense(List<CategoryGridData> categoriesExpense) {
            this.categoriesExpense = categoriesExpense;
        }

        public List<CategoryGridData> getCategoriesIncome() {
            return categoriesIncome;
        }

        public void setCategoriesIncome(List<CategoryGridData> categoriesIncome) {
            this.categoriesIncome = categoriesIncome;
        }
    }

