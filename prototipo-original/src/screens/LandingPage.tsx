import { Link } from 'react-router-dom';
import { ArrowRight, Leaf, HeartPulse, Truck, UtensilsCrossed } from 'lucide-react';
import { motion } from 'motion/react';
import { FC } from 'react';

const LandingPage: FC = () => {
  return (
    <div className="flex flex-col w-full">
      {/* Hero Section */}
      <section className="relative w-full min-h-[85vh] flex items-center justify-center overflow-hidden">
        <div className="absolute inset-0 z-0">
          <img 
            className="w-full h-full object-cover" 
            src="https://lh3.googleusercontent.com/aida-public/AB6AXuAq7vKsHOBLwJax7kwvbENJiuys8D31cIyvWNZrBqgo7RrgdF4339iZlbl3STjHpJ0LPlE8a6g_ccOZlsA0RHvvpAwmuD_1jRa48aEYzUYZMTEQ-iH-a2-K_YrxDkLhx_zws7CLH4-gvAIiMNt05cbb8Ej982kUc2fmwtIFuSEkHS-EOvz2bgudenmf0_D_ZwFYgdHvJHU_nmzU8Q5bRm8brFUYo9HI2ja6IxpuqNxMlsWmHEFle5Ex5sfErJ52S0ah7SjVbHcjHHo"
            alt="Peruvian Culinary Spread"
          />
          <div className="absolute inset-0 bg-primary/20 backdrop-blur-[2px]"></div>
          <div className="absolute inset-0 bg-gradient-to-b from-transparent via-primary/10 to-surface"></div>
        </div>
        
        <motion.div 
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.8 }}
          className="relative z-10 container mx-auto px-8 flex flex-col items-center text-center max-w-4xl"
        >
          <h1 className="font-display text-5xl md:text-7xl text-on-primary mb-6 drop-shadow-lg leading-tight">
            Gastronomía que nutre,<br />tradición que sana.
          </h1>
          <p className="font-sans text-lg md:text-xl text-on-primary/90 mb-10 max-w-2xl drop-shadow-sm">
            Descubre una experiencia culinaria premium que fusiona los superalimentos andinos con la alta cocina moderna. Diseñado para tu bienestar.
          </p>
          <div className="flex flex-col sm:flex-row gap-4 w-full sm:w-auto">
            <Link to="/menu" className="bg-primary text-on-primary font-semibold px-10 py-4 rounded-full shadow-lg shadow-primary/20 hover:scale-105 transition-transform duration-200 text-center">
              Ver menú
            </Link>
            <button className="bg-surface text-primary border border-primary font-semibold px-10 py-4 rounded-full hover:bg-primary/5 transition-colors duration-200">
              Realizar pedido
            </button>
          </div>
        </motion.div>
      </section>

      {/* Bento Grid Features */}
      <section className="py-24 px-8 max-w-7xl mx-auto w-full">
        <div className="text-center mb-16">
          <motion.h2 
            initial={{ opacity: 0 }}
            whileInView={{ opacity: 1 }}
            viewport={{ once: true }}
            className="font-display text-4xl text-primary mb-4"
          >
            Filosofía Qarmi
          </motion.h2>
          <p className="text-on-surface-variant max-w-2xl mx-auto">Nuestro compromiso con la calidad, la sostenibilidad y tu salud.</p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 auto-rows-[300px]">
          {/* Main Large Card */}
          <motion.div 
            whileHover={{ scale: 1.02 }}
            className="md:col-span-2 md:row-span-2 bg-surface-container-low rounded-3xl border border-outline-variant/30 p-10 shadow-xl shadow-primary/5 flex flex-col relative overflow-hidden group"
          >
            <div className="absolute inset-0 z-0">
              <img 
                className="w-full h-full object-cover opacity-20 group-hover:opacity-30 transition-opacity duration-500" 
                src="https://lh3.googleusercontent.com/aida-public/AB6AXuDt7ynPFVH908UrSHxW8j7dpy9VZJqpoYpldLGR4xzYXbv7Rq5oi_82qsKrzIMDvPzjM49j8Bu8BrBaoEkoE-7sSC5c_BCrUhrt60_QfPgfczFDNdcKB8Gw9VeBnU_I6XRCuz5hO5x_DuvIUTZJ6VDRyN-bHkAlPQE7e_o59Eaa_gItXzANqaZkHNsKAxPGfURqrwxGN03cguHnZRmVTeeUYNx4RQcGcWl5XgoTV46sH1BxX35tKfaqJYG-RQKw78VJ5oMVUVAjHqY" 
                alt="Superfoods"
              />
            </div>
            <div className="relative z-10 flex flex-col h-full justify-end">
              <div className="bg-surface/80 backdrop-blur-md p-4 rounded-2xl border border-outline-variant/20 inline-block self-start mb-auto">
                <Leaf className="text-primary" size={32} />
              </div>
              <h3 className="font-display text-3xl text-primary mt-6 mb-2">Ingredientes de Origen</h3>
              <p className="text-on-surface-variant max-w-md">Seleccionamos rigurosamente cada insumo, priorizando productores locales y prácticas agrícolas sostenibles en los Andes.</p>
            </div>
          </motion.div>

          {/* Small Feature 1 */}
          <motion.div 
            whileHover={{ y: -5 }}
            className="bg-surface-container-lowest rounded-3xl border border-outline-variant/30 p-8 shadow-xl shadow-primary/5 flex flex-col transition-all"
          >
            <div className="bg-secondary/10 p-3 rounded-full w-14 h-14 flex items-center justify-center mb-6 text-secondary">
              <HeartPulse size={24} />
            </div>
            <h3 className="font-display text-xl text-on-surface mb-2">Nutrición Balanceada</h3>
            <p className="text-on-surface-variant text-sm">Menús diseñados por expertos para optimizar tu bienestar diario.</p>
          </motion.div>

          {/* Small Feature 2 */}
          <motion.div 
            whileHover={{ y: -5 }}
            className="bg-surface-container-lowest rounded-3xl border border-outline-variant/30 p-8 shadow-xl shadow-primary/5 flex flex-col transition-all"
          >
            <div className="bg-tertiary/10 p-3 rounded-full w-14 h-14 flex items-center justify-center mb-6 text-tertiary">
              <Truck size={24} />
            </div>
            <h3 className="font-display text-xl text-on-surface mb-2">Logística Eficiente</h3>
            <p className="text-on-surface-variant text-sm">Entrega rápida y segura conservando la temperatura y calidad.</p>
          </motion.div>

          {/* Wide Card Action */}
          <motion.div 
            whileHover={{ scale: 1.02 }}
            className="md:col-span-1 md:row-span-1 bg-primary text-on-primary rounded-3xl p-8 shadow-lg flex flex-col justify-between overflow-hidden relative"
          >
            <div className="absolute -right-8 -bottom-8 opacity-20 rotate-12">
              <UtensilsCrossed size={160} />
            </div>
            <div className="relative z-10">
              <h3 className="font-display text-2xl mb-2">Acceso Personal</h3>
              <p className="text-on-primary/80 mb-4 text-sm">Gestión interna y reportes para el equipo Sumaq.</p>
            </div>
            <Link to="/admin" className="relative z-10 inline-flex items-center gap-2 font-semibold text-on-primary hover:underline self-start">
              Ingresar al Portal <ArrowRight size={16} />
            </Link>
          </motion.div>
        </div>
      </section>

      {/* Footer */}
      <footer className="bg-surface-container-highest w-full py-12 border-t border-outline-variant/50">
        <div className="flex flex-col md:flex-row justify-between items-center px-8 max-w-7xl mx-auto gap-8">
          <div className="font-display text-2xl font-bold text-primary">Qarmi Salud</div>
          <p className="text-on-surface-variant text-center md:text-left text-sm">
            © 2024 Qarmi Salud. Nutriendo el espíritu, honrando la tierra.
          </p>
          <nav className="flex gap-6 text-sm font-semibold">
            <a href="#" className="text-on-surface-variant hover:text-secondary transition-colors">Sostenibilidad</a>
            <a href="#" className="text-on-surface-variant hover:text-secondary transition-colors">Términos</a>
            <a href="#" className="text-on-surface-variant hover:text-secondary transition-colors">Privacidad</a>
            <a href="#" className="text-on-surface-variant hover:text-secondary transition-colors">Contacto</a>
          </nav>
        </div>
      </footer>
    </div>
  );
};

export default LandingPage;
      <section className="relative w-full min-h-[85vh] flex items-center justify-center overflow-hidden">
        <div className="absolute inset-0 z-0">
          <img 
            className="w-full h-full object-cover" 
            src="https://lh3.googleusercontent.com/aida-public/AB6AXuAq7vKsHOBLwJax7kwvbENJiuys8D31cIyvWNZrBqgo7RrgdF4339iZlbl3STjHpJ0LPlE8a6g_ccOZlsA0RHvvpAwmuD_1jRa48aEYzUYZMTEQ-iH-a2-K_YrxDkLhx_zws7CLH4-gvAIiMNt05cbb8Ej982kUc2fmwtIFuSEkHS-EOvz2bgudenmf0_D_ZwFYgdHvJHU_nmzU8Q5bRm8brFUYo9HI2ja6IxpuqNxMlsWmHEFle5Ex5sfErJ52S0ah7SjVbHcjHHo"
            alt="Peruvian Culinary Spread"
          />
          <div className="absolute inset-0 bg-primary/20 backdrop-blur-[2px]"></div>
          <div className="absolute inset-0 bg-gradient-to-b from-transparent via-primary/10 to-surface"></div>
        </div>
        
        <motion.div 
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.8 }}
          className="relative z-10 container mx-auto px-8 flex flex-col items-center text-center max-w-4xl"
        >
          <h1 className="font-display text-5xl md:text-7xl text-on-primary mb-6 drop-shadow-lg leading-tight">
            Gastronomía que nutre,<br />tradición que sana.
          </h1>
          <p className="font-sans text-lg md:text-xl text-on-primary/90 mb-10 max-w-2xl drop-shadow-sm">
            Descubre una experiencia culinaria premium que fusiona los superalimentos andinos con la alta cocina moderna. Diseñado para tu bienestar.
          </p>
          <div className="flex flex-col sm:flex-row gap-4 w-full sm:w-auto">
            <Link to="/menu" className="bg-primary text-on-primary font-semibold px-10 py-4 rounded-full shadow-lg shadow-primary/20 hover:scale-105 transition-transform duration-200 text-center">
              Ver menú
            </Link>
            <button className="bg-surface text-primary border border-primary font-semibold px-10 py-4 rounded-full hover:bg-primary/5 transition-colors duration-200">
              Realizar pedido
            </button>
          </div>
        </motion.div>
      </section>

      {/* Bento Grid Features */}
      <section className="py-24 px-8 max-w-7xl mx-auto w-full">
        <div className="text-center mb-16">
          <motion.h2 
            initial={{ opacity: 0 }}
            whileInView={{ opacity: 1 }}
            viewport={{ once: true }}
            className="font-display text-4xl text-primary mb-4"
          >
            Filosofía Qarmi
          </motion.h2>
          <p className="text-on-surface-variant max-w-2xl mx-auto">Nuestro compromiso con la calidad, la sostenibilidad y tu salud.</p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 auto-rows-[300px]">
          {/* Main Large Card */}
          <motion.div 
            whileHover={{ scale: 1.02 }}
            className="md:col-span-2 md:row-span-2 bg-surface-container-low rounded-3xl border border-outline-variant/30 p-10 shadow-xl shadow-primary/5 flex flex-col relative overflow-hidden group"
          >
            <div className="absolute inset-0 z-0">
              <img 
                className="w-full h-full object-cover opacity-20 group-hover:opacity-30 transition-opacity duration-500" 
                src="https://lh3.googleusercontent.com/aida-public/AB6AXuDt7ynPFVH908UrSHxW8j7dpy9VZJqpoYpldLGR4xzYXbv7Rq5oi_82qsKrzIMDvPzjM49j8Bu8BrBaoEkoE-7sSC5c_BCrUhrt60_QfPgfczFDNdcKB8Gw9VeBnU_I6XRCuz5hO5x_DuvIUTZJ6VDRyN-bHkAlPQE7e_o59Eaa_gItXzANqaZkHNsKAxPGfURqrwxGN03cguHnZRmVTeeUYNx4RQcGcWl5XgoTV46sH1BxX35tKfaqJYG-RQKw78VJ5oMVUVAjHqY" 
                alt="Superfoods"
              />
            </div>
            <div className="relative z-10 flex flex-col h-full justify-end">
              <div className="bg-surface/80 backdrop-blur-md p-4 rounded-2xl border border-outline-variant/20 inline-block self-start mb-auto">
                <Leaf className="text-primary" size={32} />
              </div>
              <h3 className="font-display text-3xl text-primary mt-6 mb-2">Ingredientes de Origen</h3>
              <p className="text-on-surface-variant max-w-md">Seleccionamos rigurosamente cada insumo, priorizando productores locales y prácticas agrícolas sostenibles en los Andes.</p>
            </div>
          </motion.div>

          {/* Small Feature 1 */}
          <motion.div 
            whileHover={{ y: -5 }}
            className="bg-surface-container-lowest rounded-3xl border border-outline-variant/30 p-8 shadow-xl shadow-primary/5 flex flex-col transition-all"
          >
            <div className="bg-secondary/10 p-3 rounded-full w-14 h-14 flex items-center justify-center mb-6 text-secondary">
              <HeartPulse size={24} />
            </div>
            <h3 className="font-display text-xl text-on-surface mb-2">Nutrición Balanceada</h3>
            <p className="text-on-surface-variant text-sm">Menús diseñados por expertos para optimizar tu bienestar diario.</p>
          </motion.div>

          {/* Small Feature 2 */}
          <motion.div 
            whileHover={{ y: -5 }}
            className="bg-surface-container-lowest rounded-3xl border border-outline-variant/30 p-8 shadow-xl shadow-primary/5 flex flex-col transition-all"
          >
            <div className="bg-tertiary/10 p-3 rounded-full w-14 h-14 flex items-center justify-center mb-6 text-tertiary">
              <Truck size={24} />
            </div>
            <h3 className="font-display text-xl text-on-surface mb-2">Logística Eficiente</h3>
            <p className="text-on-surface-variant text-sm">Entrega rápida y segura conservando la temperatura y calidad.</p>
          </motion.div>

          {/* Wide Card Action */}
          <motion.div 
            whileHover={{ scale: 1.02 }}
            className="md:col-span-1 md:row-span-1 bg-primary text-on-primary rounded-3xl p-8 shadow-lg flex flex-col justify-between overflow-hidden relative"
          >
            <div className="absolute -right-8 -bottom-8 opacity-20 rotate-12">
              <UtensilsCrossed size={160} />
            </div>
            <div className="relative z-10">
              <h3 className="font-display text-2xl mb-2">Acceso Personal</h3>
              <p className="text-on-primary/80 mb-4 text-sm">Gestión interna y reportes para el equipo Sumaq.</p>
            </div>
            <Link to="/admin" className="relative z-10 inline-flex items-center gap-2 font-semibold text-on-primary hover:underline self-start">
              Ingresar al Portal <ArrowRight size={16} />
            </Link>
          </motion.div>
        </div>
      </section>

      {/* Footer */}
      <footer className="bg-surface-container-highest w-full py-12 border-t border-outline-variant/50">
        <div className="flex flex-col md:flex-row justify-between items-center px-8 max-w-7xl mx-auto gap-8">
          <div className="font-display text-2xl font-bold text-primary">Qarmi Salud</div>
          <p className="text-on-surface-variant text-center md:text-left text-sm">
            © 2024 Qarmi Salud. Nutriendo el espíritu, honrando la tierra.
          </p>
          <nav className="flex gap-6 text-sm font-semibold">
            <a href="#" className="text-on-surface-variant hover:text-secondary transition-colors">Sostenibilidad</a>
            <a href="#" className="text-on-surface-variant hover:text-secondary transition-colors">Términos</a>
            <a href="#" className="text-on-surface-variant hover:text-secondary transition-colors">Privacidad</a>
            <a href="#" className="text-on-surface-variant hover:text-secondary transition-colors">Contacto</a>
          </nav>
        </div>
      </footer>
    </div>
  );
}
