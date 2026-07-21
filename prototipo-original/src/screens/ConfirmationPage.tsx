import { CheckCircle2, Clock, CookingPot, ShoppingBag, Receipt, ArrowLeft } from 'lucide-react';
import { Link } from 'react-router-dom';
import { motion } from 'motion/react';
import { FC } from 'react';

const ConfirmationPage: FC = () => {
  return (
    <div className="pt-28 pb-24 px-8 max-w-6xl mx-auto w-full min-h-[90vh] flex flex-col font-sans">
      <div className="grid grid-cols-1 md:grid-cols-12 gap-12 items-stretch h-full">
        {/* Main Canvas */}
        <motion.div 
          initial={{ opacity: 0, scale: 0.95 }}
          animate={{ opacity: 1, scale: 1 }}
          className="md:col-span-8 bg-surface-container-low rounded-[2.5rem] border border-outline-variant/50 p-10 md:p-16 shadow-2xl shadow-primary/5 flex flex-col items-center text-center relative overflow-hidden"
        >
          <div className="absolute top-[-100px] right-[-100px] w-64 h-64 bg-primary/5 rounded-full blur-[100px] pointer-events-none"></div>
          <div className="absolute bottom-[-100px] left-[-100px] w-64 h-64 bg-tertiary/5 rounded-full blur-[100px] pointer-events-none"></div>
          
          <div className="w-24 h-24 rounded-full bg-primary/10 flex items-center justify-center mb-8 ring-8 ring-primary/5">
            <CheckCircle2 className="text-primary" size={56} />
          </div>
          
          <h1 className="font-display text-5xl text-on-surface mb-6 leading-tight">¡Pedido Confirmado!</h1>
          <p className="text-lg text-on-surface-variant mb-12 max-w-lg leading-relaxed">
            Tu pedido en Qarmi Salud está siendo preparado con los ingredientes más frescos. Te avisaremos cuando esté listo para recoger.
          </p>

          <div className="bg-surface rounded-3xl border border-outline-variant/40 p-10 w-full max-w-md shadow-lg shadow-black/5 mb-12">
            <p className="text-[10px] font-bold text-on-surface-variant uppercase tracking-widest mb-2">Código de Recojo</p>
            <p className="font-display text-7xl text-primary font-bold tracking-tight">Q-842</p>
          </div>

          <div className="flex items-center gap-4 bg-tertiary/5 px-8 py-5 rounded-full border border-tertiary/20">
            <Clock className="text-tertiary" size={24} />
            <div className="text-left font-sans">
              <p className="text-[10px] font-bold text-on-surface-variant uppercase tracking-widest leading-none">Tiempo estimado</p>
              <p className="font-bold text-on-surface leading-tight">15 - 20 minutos</p>
            </div>
          </div>
        </motion.div>

        {/* Sidebar Info */}
        <div className="md:col-span-4 flex flex-col gap-6">
          <div className="bg-surface-container-lowest rounded-[2rem] border border-outline-variant/30 p-8 shadow-xl shadow-primary/5 flex-1">
            <h2 className="font-display text-2xl text-on-surface mb-10">Estado del Pedido</h2>
            <div className="relative pl-2">
              <div className="absolute left-[15px] top-[24px] bottom-[24px] w-[2px] bg-outline-variant/20"></div>
              <div className="absolute left-[15px] top-[24px] h-[40%] w-[2px] bg-primary"></div>
              
              <div className="flex gap-6 mb-10 relative">
                <div className="w-8 h-8 rounded-full bg-primary flex items-center justify-center z-10 shadow-lg shadow-primary/20">
                  <CheckCircle2 className="text-on-primary" size={16} />
                </div>
                <div>
                  <p className="font-bold text-sm text-on-surface">Pagado</p>
                  <p className="text-xs text-on-surface-variant">12:30 PM</p>
                </div>
              </div>

              <div className="flex gap-6 mb-10 relative">
                <div className="w-8 h-8 rounded-full bg-primary-container flex items-center justify-center z-10 ring-4 ring-surface-container-lowest">
                  <CookingPot className="text-on-primary-container" size={16} />
                </div>
                <div>
                  <p className="font-bold text-sm text-primary">En cocina</p>
                  <p className="text-xs text-primary/70">Preparando ahora</p>
                </div>
              </div>

              <div className="flex gap-6 relative">
                <div className="w-8 h-8 rounded-full bg-surface-container-high flex items-center justify-center z-10 text-outline ring-4 ring-surface-container-lowest">
                  <ShoppingBag size={16} />
                </div>
                <div>
                  <p className="font-bold text-sm text-on-surface-variant opacity-50">Listo para recoger</p>
                  <p className="text-[10px] uppercase font-bold text-outline opacity-50 tracking-widest mt-1">Estimado 12:45 PM</p>
                </div>
              </div>
            </div>
          </div>

          <div className="bg-surface-container-lowest rounded-[2rem] border border-outline-variant/30 p-8 shadow-xl shadow-primary/5">
            <h3 className="text-[10px] font-bold text-on-surface-variant uppercase tracking-widest mb-6">Resumen</h3>
            <div className="space-y-4 mb-8">
              <div className="flex justify-between text-sm font-semibold">
                <span>Bowl Andino Imperial</span>
                <span>x1</span>
              </div>
            </div>
            <div className="pt-6 border-t border-outline-variant/20 flex justify-between items-center mb-8">
              <span className="text-sm font-bold text-on-surface-variant">Total</span>
              <span className="font-display text-2xl font-bold text-primary">S/ 42.00</span>
            </div>
            <button className="w-full py-4 text-xs font-bold uppercase tracking-widest border border-outline-variant rounded-full hover:bg-surface-variant transition-colors flex items-center justify-center gap-2">
              <Receipt size={16} /> Ver Recibo
            </button>
          </div>
        </div>
      </div>
      
      <div className="mt-12 text-center">
        <Link to="/" className="text-primary font-bold hover:underline inline-flex items-center gap-2">
          <ArrowLeft size={16} /> Volver al Inicio
        </Link>
      </div>
    </div>
  );
};

export default ConfirmationPage;
