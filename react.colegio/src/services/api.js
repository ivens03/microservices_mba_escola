import axios from 'axios';

// Lê a URL do arquivo .env ou usa o padrão
const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080';

// Credenciais temporárias (Basic Auth) - Ajuste conforme necessário
const username = 'admin';
const password = 'admin123';
const token = btoa(`${username}:${password}`);

const api = axios.create({
    baseURL: API_URL,
    headers: {
        'Content-Type': 'application/json',
        'Authorization': `Basic ${token}`
    }
});

// Interceptor para logar erros (opcional)
api.interceptors.response.use(
    response => response,
    error => {
        console.error("Erro na API:", error.response?.data || error.message);
        return Promise.reject(error);
    }
);

export default api;