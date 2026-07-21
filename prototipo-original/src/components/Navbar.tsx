import { Link, useLocation } from 'react-router-dom';
import { ShoppingCart, User, Menu, X } from 'lucide-react';
import { cn } from '../lib/utils';
import { useState, FC } from 'react';

const Navbar: FC = () => {
  const [isMenuOpen, setIsMenuOpen] = useState<boolean>(false);
  const location = useLocation();
  const isAdmin = location.pathname.startsWith('/admin');

  if (isAdmin) return null;

  const navLinks: Array<{ name: string; href: string }> = [
    { name: 'Menú', href: '/menu' },
    { name: 'Salud', href: '#' },
    { name: 'Historia', href: '#' },
    { name: 'Locales', href: '#' },
  ];

  const handleMenuToggle = (): void => {
    setIsMenuOpen(!isMenuOpen);
  };

  const handleLinkClick = (): void => {
    setIsMenuOpen(false);
  };

  return (
    <header className="fixed top-0 w-full z-50 bg-surface/80 backdrop-blur-lg border-b border-outline-variant/30 shadow-sm">
      <div className="flex justify-between items-center px-6 py-3 max-w-7xl mx-auto">
        <div className="flex items-center gap-8">
          <Link to="/" className="font-display text-2xl font-bold text-primary group transition-all">
            Qarmi <span className="text-secondary group-hover:text-tertiary transition-colors">Salud</span>
          </Link>
          <nav className="hidden md:flex gap-6">
            {navLinks.map((link) => (
              <Link
                key={link.name}
                to={link.href}
                className={cn(
                  "font-sans text-sm font-semibold text-on-surface-variant hover:text-primary transition-all px-2 py-1 rounded-lg",
                  location.pathname === link.href && "text-primary border-b-2 border-primary rounded-none"
                )}
              >
                {link.name}
              </Link>
            ))}
          </nav>
        </div>

        <div className="flex items-center gap-4">
          <Link to="/admin" className="hidden md:flex bg-primary text-on-primary text-sm font-semibold px-6 py-2 rounded-full hover:scale-95 transition-transform duration-200">
            Pedir Ahora
          </Link>
          <div className="flex items-center gap-2">
            <button className="p-2 text-on-surface-variant hover:text-primary transition-colors hover:bg-primary/5 rounded-lg">
              <ShoppingCart size={20} />
            </button>
            <button className="p-2 text-on-surface-variant hover:text-primary transition-colors hover:bg-primary/5 rounded-lg">
              <User size={20} />
            </button>
            <button 
              className="md:hidden p-2 text-on-surface-variant hover:text-primary transition-colors hover:bg-primary/5 rounded-lg"
              onClick={handleMenuToggle}
            >
              {isMenuOpen ? <X size={20} /> : <Menu size={20} />}
            </button>
          </div>
        </div>
      </div>

      {/* Mobile Menu */}
      {isMenuOpen && (
        <div className="md:hidden bg-surface border-b border-outline-variant/30 px-6 py-4 flex flex-col gap-4">
          {navLinks.map((link) => (
            <Link
              key={link.name}
              to={link.href}
              className="font-sans text-lg font-semibold text-on-surface-variant hover:text-primary py-2"
              onClick={handleLinkClick}
            >
              {link.name}
            </Link>
          ))}
          <Link 
            to="/admin" 
            className="bg-primary text-on-primary text-center py-3 rounded-full font-semibold"
            onClick={handleLinkClick}
          >
            Pedir Ahora
          </Link>
        </div>
      )}
    </header>
  );
};

export default Navbar;
