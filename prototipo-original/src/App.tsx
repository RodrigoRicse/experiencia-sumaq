import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import LandingPage from './screens/LandingPage';
import MenuPage from './screens/MenuPage';
import ProductDetail from './screens/ProductDetail';
import AdminDashboard from './screens/AdminDashboard';
import KitchenDashboard from './screens/KitchenDashboard';
import CashierDashboard from './screens/CashierDashboard';
import ReportsDashboard from './screens/ReportsDashboard';
import CheckoutPage from './screens/CheckoutPage';
import ConfirmationPage from './screens/ConfirmationPage';

export default function App() {
  return (
    <Router>
      <div className="min-h-screen bg-surface selection:bg-primary/20 selection:text-primary">
        <Navbar />
        <Routes>
          <Route path="/" element={<LandingPage />} />
          <Route path="/menu" element={<MenuPage />} />
          <Route path="/product" element={<ProductDetail />} />
          <Route path="/checkout" element={<CheckoutPage />} />
          <Route path="/confirmation" element={<ConfirmationPage />} />
          
          {/* Admin Routes */}
          <Route path="/admin" element={<AdminDashboard />} />
          <Route path="/admin/kitchen" element={<KitchenDashboard />} />
          <Route path="/admin/cashier" element={<CashierDashboard />} />
          <Route path="/admin/reports" element={<ReportsDashboard />} />
        </Routes>
      </div>
    </Router>
  );
}
