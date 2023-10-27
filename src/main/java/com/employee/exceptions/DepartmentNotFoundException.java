package com.employee.exceptions;

import java.io.IOException;

public class DepartmentNotFoundException extends IOException {
    public DepartmentNotFoundException(String message) {
        super(message);
    }
}
