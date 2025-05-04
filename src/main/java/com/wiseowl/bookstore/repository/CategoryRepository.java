package com.wiseowl.bookstore.repository;
import com.wiseowl.bookstore.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {}
