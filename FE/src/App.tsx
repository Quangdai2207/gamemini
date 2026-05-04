import {Routes, Route} from "react-router-dom";
import Login from "./pages/Login";
import Home from "./pages/Home";
import ProtectedRoute from "./components/ProtectedRoute.tsx";
import {AuthProvider} from "./context/AuthContext.tsx";
import PublicRoute from "./components/PublicRoute.tsx";


function AppRoutes() {

    return (
        <Routes>
            <Route
                path="/login" element={
                <PublicRoute>
                    <Login/>
                </PublicRoute>
            }
            />

            <Route
                path="/"
                element={
                    <ProtectedRoute>
                        <Home/>
                    </ProtectedRoute>
                }
            />
        </Routes>
    );
}

export default function App() {
    return (
        <AuthProvider>
            <AppRoutes/>
        </AuthProvider>
    );
}