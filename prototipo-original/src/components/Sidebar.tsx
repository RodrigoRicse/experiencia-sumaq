import { Link, useLocation } from 'react-router-dom';
import { 
  LayoutDashboard, 
  Receipt, 
  Utensils, 
  CookingPot, 
  Wallet, 
  BarChart3, 
  Box, 
  Users, 
  Settings,
  PlusCircle,
  LucideIcon
} from 'lucide-react';
import { cn } from '../lib/utils';
import { NavItem } from '../types';

interface SidebarProps {
  currentPath?: string;
}

export default function Sidebar({ currentPath }: SidebarProps) {
  const location = useLocation();
  const pathname = currentPath || location.pathname;

  const navItems: NavItem[] = [
    { name: 'Dashboard', href: '/admin', icon: LayoutDashboard },
    { name: 'Pedidos', href: '#', icon: Receipt },
    { name: 'Menú', href: '#', icon: Utensils },
    { name: 'Cocina', href: '/admin/kitchen', icon: CookingPot },
    { name: 'Caja', href: '/admin/cashier', icon: Wallet },
    { name: 'Reportes', href: '/admin/reports', icon: BarChart3 },
    { name: 'Productos', href: '#', icon: Box },
    { name: 'Usuarios', href: '#', icon: Users },
    { name: 'Configuración', href: '#', icon: Settings },
  ];

  return (
    <aside className="fixed left-0 top-0 h-full w-64 z-40 bg-surface-container-low/95 backdrop-blur-2xl border-r border-outline-variant/20 shadow-xl shadow-primary/5 flex flex-col p-4">
      <div className="mb-10 px-2 mt-4">
        <h1 className="font-display text-2xl font-bold text-primary tracking-tight">Sumaq Admin</h1>
        <p className="font-sans text-xs font-medium text-on-surface-variant">Gestión Premium</p>
      </div>

      <nav className="flex-1 flex flex-col gap-1 overflow-y-auto pr-2 custom-scrollbar">
        {navItems.map((item: NavItem) => {
          const isActive = pathname === item.href;
          const IconComponent = item.icon as LucideIcon;
          return (
            <Link
              key={item.name}
              to={item.href}
              className={cn(
                "flex items-center gap-4 px-4 py-3 rounded-xl transition-all duration-200 group hover:translate-x-1",
                isActive 
                  ? "bg-primary text-on-primary shadow-md brightness-95" 
                  : "text-on-surface-variant hover:bg-surface-variant/40"
              )}
            >
              <IconComponent size={20} className={cn(isActive ? "fill-on-primary/10" : "group-hover:text-primary transition-colors")} />
              <span className="font-sans text-sm font-semibold">{item.name}</span>
            </Link>
          );
        })}
      </nav>

      <div className="mt-auto pt-6">
        <button className="w-full bg-tertiary text-on-tertiary font-semibold py-4 px-6 rounded-full shadow-md hover:opacity-90 transition-opacity flex items-center justify-center gap-2">
          <PlusCircle size={20} />
          Nueva Venta
        </button>
      </div>
    </aside>
  );
}
