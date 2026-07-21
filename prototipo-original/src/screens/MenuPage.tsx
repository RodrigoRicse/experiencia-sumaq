import { useState, FC } from 'react';
import { ShoppingCart, Plus, Minus, ArrowRight, Trash2 } from 'lucide-react';
import { Link } from 'react-router-dom';
import { PRODUCTS } from '../constants';
import { Product, CartItem } from '../types';
import { cn } from '../lib/utils';
import { motion, AnimatePresence } from 'motion/react';

type CategoryType = 'Entradas' | 'Fondos' | 'Bebidas' | 'Postres';

const MenuPage: FC = () => {
  const [cart, setCart] = useState<CartItem[]>([]);
  const [category, setCategory] = useState<CategoryType>('Entradas');

  const addToCart = (product: Product): void => {
    setCart(prev => {
      const existing = prev.find(item => item.product.id === product.id);
      if (existing) {
        return prev.map(item => 
          item.product.id === product.id ? { ...item, quantity: item.quantity + 1 } : item
        );
      }
      return [...prev, { product, quantity: 1 }];
    });
  };

  const updateQuantity = (id: string, delta: number): void => {
    setCart(prev => prev.map(item => {
      if (item.product.id === id) {
        const newQty = Math.max(0, item.quantity + delta);
        return { ...item, quantity: newQty };
      }
      return item;
    }).filter(item => item.quantity > 0));
  };

  const total: number = cart.reduce((acc, item) => acc + item.product.price * item.quantity, 0);
  const filteredProducts: Product[] = PRODUCTS.filter(p => p.category === category);

  return (
    <div className="pt-24 pb-12 px-8 max-w-[1440px] mx-auto w-full grid grid-cols-12 gap-8 min-h-screen">
      {/* Categories */}
      <aside className="col-span-12 md:col-span-2">
        <div className="sticky top-28">
          <h2 className="font-display text-2xl text-on-surface mb-8">Categorías</h2>
          <nav className="flex flex-col gap-2">
            {(['Entradas', 'Fondos', 'Bebidas', 'Postres'] as const).map(cat => (
              <button 
                key={cat}
                onClick={() => setCategory(cat)}
                className={cn(
                  "text-left w-full px-5 py-3 rounded-xl font-bold text-sm transition-all",
                  category === cat 
                    ? "bg-primary text-on-primary shadow-md" 
                    : "text-on-surface-variant hover:bg-surface-variant/50 hover:text-primary"
                )}
              >
                {cat}
              </button>
            ))}
          </nav>
        </div>
      </aside>

      {/* Main Grid */}
      <section className="col-span-12 md:col-span-7">
        <motion.h1 
          key={category}
          initial={{ opacity: 0, x: -20 }}
          animate={{ opacity: 1, x: 0 }}
          className="font-display text-5xl text-primary mb-12"
        >
          {category} Saludables
        </motion.h1>
        
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
          {filteredProducts.map((product: Product) => (
            <motion.article 
              layout
              key={product.id}
              className="bg-surface-container-low rounded-3xl border border-outline-variant/30 shadow-xl shadow-primary/5 overflow-hidden flex flex-col transition-all hover:-translate-y-1 hover:shadow-2xl"
            >
              <div className="h-48 bg-surface-variant relative group">
                <img src={product.image} alt={product.name} className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500" />
                {product.tags?.[0] && (
                  <div className="absolute top-3 right-3 bg-surface/90 backdrop-blur-md px-3 py-1 rounded-full border border-outline-variant/50 text-[10px] font-bold text-primary uppercase tracking-widest">
                    {product.tags[0]}
                  </div>
                )}
              </div>
              <div className="p-6 flex flex-col flex-grow">
                <h3 className="font-display text-lg text-on-surface mb-2 leading-tight">{product.name}</h3>
                <p className="text-xs text-on-surface-variant mb-6 line-clamp-2">{product.description}</p>
                <div className="flex items-center justify-between mt-auto">
                  <span className="font-display text-xl text-primary font-bold">S/ {product.price.toFixed(2)}</span>
                  <button 
                    onClick={() => addToCart(product)}
                    className="bg-secondary-container text-on-secondary-container rounded-full w-10 h-10 flex items-center justify-center hover:bg-secondary hover:text-on-secondary transition-all shadow-sm active:scale-90"
                  >
                    <Plus size={20} />
                  </button>
                </div>
              </div>
            </motion.article>
          ))}
        </div>
      </section>

      {/* Persistent Cart */}
      <aside className="col-span-12 md:col-span-3">
        <div className="sticky top-28 bg-surface-container-low rounded-[2rem] border border-outline-variant/30 shadow-xl shadow-primary/5 overflow-hidden flex flex-col h-[calc(100vh-140px)]">
          <div className="p-6 border-b border-outline-variant/20 bg-surface-container-highest/30 flex justify-between items-center">
            <h2 className="font-display text-xl text-on-surface">Tu Pedido</h2>
            <span className="bg-primary/10 text-primary px-3 py-1 rounded-full text-xs font-bold">{cart.length} items</span>
          </div>
          
          <div className="flex-grow p-6 overflow-y-auto flex flex-col gap-6 custom-scrollbar">
            <AnimatePresence>
              {cart.map((item: CartItem) => (
                <motion.div 
                  initial={{ opacity: 0, x: 20 }}
                  animate={{ opacity: 1, x: 0 }}
                  exit={{ opacity: 0, x: -20 }}
                  key={item.product.id} 
                  className="flex gap-4 items-start"
                >
                  <img src={item.product.image} alt={item.product.name} className="w-16 h-16 rounded-xl object-cover shrink-0 border border-outline-variant/20" />
                  <div className="flex-grow">
                    <h4 className="font-semibold text-on-surface text-sm">{item.product.name}</h4>
                    <span className="text-xs text-on-surface-variant block mb-2">S/ {item.product.price.toFixed(2)}</span>
                    <div className="flex items-center justify-between">
                      <div className="flex items-center gap-3">
                        <button 
                          onClick={() => updateQuantity(item.product.id, -1)}
                          className="w-6 h-6 rounded-full border border-outline flex items-center justify-center text-on-surface-variant hover:bg-surface-variant transition-colors"
                        >
                          <Minus size={12} />
                        </button>
                        <span className="font-bold text-sm w-4 text-center">{item.quantity}</span>
                        <button 
                          onClick={() => updateQuantity(item.product.id, 1)}
                          className="w-6 h-6 rounded-full border border-outline flex items-center justify-center text-on-surface-variant hover:bg-surface-variant transition-colors"
                        >
                          <Plus size={12} />
                        </button>
                      </div>
                      <button 
                        onClick={() => updateQuantity(item.product.id, -item.quantity)}
                        className="text-outline hover:text-error transition-colors"
                      >
                        <Trash2 size={14} />
                      </button>
                    </div>
                  </div>
                </motion.div>
              ))}
            </AnimatePresence>
            {cart.length === 0 && (
              <div className="flex flex-col items-center justify-center h-full text-on-surface-variant/40 gap-4">
                <ShoppingCart size={48} strokeWidth={1} />
                <p className="font-semibold text-sm">Tu carrito está vacío</p>
              </div>
            )}
          </div>

          <div className="p-6 bg-surface-container-highest border-t border-outline-variant/20 mt-auto">
            <div className="flex justify-between mb-2 text-sm font-semibold text-on-surface-variant">
              <span>Subtotal</span>
              <span>S/ {total.toFixed(2)}</span>
            </div>
            <div className="flex justify-between mb-6">
              <span className="font-display text-xl text-on-surface">Total</span>
              <span className="font-display text-xl text-primary font-bold">S/ {total.toFixed(2)}</span>
            </div>
            <Link 
              to="/checkout"
              className={cn(
                "w-full bg-primary text-on-primary rounded-full py-4 font-bold shadow-md hover:brightness-110 transition-all flex items-center justify-center gap-2",
                cart.length === 0 && "opacity-50 pointer-events-none grayscale"
              )}
            >
              Procesar Pago <ArrowRight size={18} />
            </Link>
          </div>
        </div>
      </aside>
    </div>
  );
};

export default MenuPage;

  return (
    <div className="pt-24 pb-12 px-8 max-w-[1440px] mx-auto w-full grid grid-cols-12 gap-8 min-h-screen">
      {/* Categories */}
      <aside className="col-span-12 md:col-span-2">
        <div className="sticky top-28">
          <h2 className="font-display text-2xl text-on-surface mb-8">Categorías</h2>
          <nav className="flex flex-col gap-2">
            {['Entradas', 'Fondos', 'Bebidas', 'Postres'].map(cat => (
              <button 
                key={cat}
                onClick={() => setCategory(cat)}
                className={cn(
                  "text-left w-full px-5 py-3 rounded-xl font-bold text-sm transition-all",
                  category === cat 
                    ? "bg-primary text-on-primary shadow-md" 
                    : "text-on-surface-variant hover:bg-surface-variant/50 hover:text-primary"
                )}
              >
                {cat}
              </button>
            ))}
          </nav>
        </div>
      </aside>

      {/* Main Grid */}
      <section className="col-span-12 md:col-span-7">
        <motion.h1 
          key={category}
          initial={{ opacity: 0, x: -20 }}
          animate={{ opacity: 1, x: 0 }}
          className="font-display text-5xl text-primary mb-12"
        >
          {category} Saludables
        </motion.h1>
        
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
          {filteredProducts.map((product) => (
            <motion.article 
              layout
              key={product.id}
              className="bg-surface-container-low rounded-3xl border border-outline-variant/30 shadow-xl shadow-primary/5 overflow-hidden flex flex-col transition-all hover:-translate-y-1 hover:shadow-2xl"
            >
              <div className="h-48 bg-surface-variant relative group">
                <img src={product.image} alt={product.name} className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500" />
                {product.tags?.[0] && (
                  <div className="absolute top-3 right-3 bg-surface/90 backdrop-blur-md px-3 py-1 rounded-full border border-outline-variant/50 text-[10px] font-bold text-primary uppercase tracking-widest">
                    {product.tags[0]}
                  </div>
                )}
              </div>
              <div className="p-6 flex flex-col flex-grow">
                <h3 className="font-display text-lg text-on-surface mb-2 leading-tight">{product.name}</h3>
                <p className="text-xs text-on-surface-variant mb-6 line-clamp-2">{product.description}</p>
                <div className="flex items-center justify-between mt-auto">
                  <span className="font-display text-xl text-primary font-bold">S/ {product.price.toFixed(2)}</span>
                  <button 
                    onClick={() => addToCart(product)}
                    className="bg-secondary-container text-on-secondary-container rounded-full w-10 h-10 flex items-center justify-center hover:bg-secondary hover:text-on-secondary transition-all shadow-sm active:scale-90"
                  >
                    <Plus size={20} />
                  </button>
                </div>
              </div>
            </motion.article>
          ))}
        </div>
      </section>

      {/* Persistent Cart */}
      <aside className="col-span-12 md:col-span-3">
        <div className="sticky top-28 bg-surface-container-low rounded-[2rem] border border-outline-variant/30 shadow-xl shadow-primary/5 overflow-hidden flex flex-col h-[calc(100vh-140px)]">
          <div className="p-6 border-b border-outline-variant/20 bg-surface-container-highest/30 flex justify-between items-center">
            <h2 className="font-display text-xl text-on-surface">Tu Pedido</h2>
            <span className="bg-primary/10 text-primary px-3 py-1 rounded-full text-xs font-bold">{cart.length} items</span>
          </div>
          
          <div className="flex-grow p-6 overflow-y-auto flex flex-col gap-6 custom-scrollbar">
            <AnimatePresence>
              {cart.map((item) => (
                <motion.div 
                  initial={{ opacity: 0, x: 20 }}
                  animate={{ opacity: 1, x: 0 }}
                  exit={{ opacity: 0, x: -20 }}
                  key={item.product.id} 
                  className="flex gap-4 items-start"
                >
                  <img src={item.product.image} className="w-16 h-16 rounded-xl object-cover shrink-0 border border-outline-variant/20" />
                  <div className="flex-grow">
                    <h4 className="font-semibold text-on-surface text-sm">{item.product.name}</h4>
                    <span className="text-xs text-on-surface-variant block mb-2">S/ {item.product.price.toFixed(2)}</span>
                    <div className="flex items-center justify-between">
                      <div className="flex items-center gap-3">
                        <button 
                          onClick={() => updateQuantity(item.product.id, -1)}
                          className="w-6 h-6 rounded-full border border-outline flex items-center justify-center text-on-surface-variant hover:bg-surface-variant transition-colors"
                        >
                          <Minus size={12} />
                        </button>
                        <span className="font-bold text-sm w-4 text-center">{item.quantity}</span>
                        <button 
                          onClick={() => updateQuantity(item.product.id, 1)}
                          className="w-6 h-6 rounded-full border border-outline flex items-center justify-center text-on-surface-variant hover:bg-surface-variant transition-colors"
                        >
                          <Plus size={12} />
                        </button>
                      </div>
                      <button 
                        onClick={() => updateQuantity(item.product.id, -item.quantity)}
                        className="text-outline hover:text-error transition-colors"
                      >
                        <Trash2 size={14} />
                      </button>
                    </div>
                  </div>
                </motion.div>
              ))}
            </AnimatePresence>
            {cart.length === 0 && (
              <div className="flex flex-col items-center justify-center h-full text-on-surface-variant/40 gap-4">
                <ShoppingCart size={48} strokeWidth={1} />
                <p className="font-semibold text-sm">Tu carrito está vacío</p>
              </div>
            )}
          </div>

          <div className="p-6 bg-surface-container-highest border-t border-outline-variant/20 mt-auto">
            <div className="flex justify-between mb-2 text-sm font-semibold text-on-surface-variant">
              <span>Subtotal</span>
              <span>S/ {total.toFixed(2)}</span>
            </div>
            <div className="flex justify-between mb-6">
              <span className="font-display text-xl text-on-surface">Total</span>
              <span className="font-display text-xl text-primary font-bold">S/ {total.toFixed(2)}</span>
            </div>
            <Link 
              to="/checkout"
              className={cn(
                "w-full bg-primary text-on-primary rounded-full py-4 font-bold shadow-md hover:brightness-110 transition-all flex items-center justify-center gap-2",
                cart.length === 0 && "opacity-50 pointer-events-none grayscale"
              )}
            >
              Procesar Pago <ArrowRight size={18} />
            </Link>
          </div>
        </div>
      </aside>
    </div>
  );
}
