import {createContext, useContext, useEffect, useState} from "react";

type AuthContextType = {
    isAuthenticated: boolean;
    setIsAuthenticated: (v: boolean) => void;
    loading: boolean;
};

const AuthContext = createContext<AuthContextType | null>(null);

export const useAuth = () => useContext(AuthContext)!;

export const AuthProvider = ({children}: { children: React.ReactNode }) => {

    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [loading, setLoading] = useState(true);

    const checkAuth = async () => {
        try {
            const res = await fetch("http://localhost:8080/api/v1/auth/authenticated", {
                method: "GET",
                credentials: "include"
            });

            const data = await res.json();
            const {status} = data;
            if (status === 200) setIsAuthenticated(true);
            else if (status === 401) setIsAuthenticated(false);
        } catch {
            setIsAuthenticated(false);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        checkAuth();
    }, []);

    return (
        <AuthContext.Provider value={{isAuthenticated, setIsAuthenticated, loading}}>
            {children}
        </AuthContext.Provider>
    );
};