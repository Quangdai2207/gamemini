import { Navigate } from "react-router-dom";
import type {JSX} from "react";
import {useAuth} from "../context/AuthContext.tsx";

export default function PublicRoute({ children }: { children: JSX.Element }) {

    const { isAuthenticated, loading } = useAuth();

    if (loading) return <div>Loading...</div>;

    if (isAuthenticated) return <Navigate to="/" replace />;

    return children;
}