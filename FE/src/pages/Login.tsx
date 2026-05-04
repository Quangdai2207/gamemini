import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function Login() {

    const { setIsAuthenticated } = useAuth();
    const navigate = useNavigate();

    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        const res = await fetch("http://localhost:8080/api/v1/auth/login", {
            method: "POST",
            credentials: "include",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ username, password })
        });

        if (res.ok) {
            setIsAuthenticated(res.status === 200);
            navigate("/", { replace: true });
        } else {
            alert("Sai tài khoản hoặc mật khẩu");
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <input
                value={username}
                onChange={e => setUsername(e.target.value)}
                placeholder="username"
            />
            <input
                type="password"
                value={password}
                onChange={e => setPassword(e.target.value)}
                placeholder="password"
            />
            <button type="submit">Login</button>
        </form>
    );
}