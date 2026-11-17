package com.universidad.posbodega.service;

import com.universidad.posbodega.dto.AuthRequest;
import com.universidad.posbodega.dto.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthRequest request);
}