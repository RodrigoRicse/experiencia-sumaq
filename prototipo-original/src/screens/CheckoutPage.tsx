import { User, MapPin, CreditCard, Lock, Smartphone, Timer, ShieldCheck, ArrowLeft } from 'lucide-react';
import { Link } from 'react-router-dom';
import { cn } from '../lib/utils';
import { useState, FC } from 'react';
import { CheckoutFormData } from '../types';

type PaymentMethod = 'card' | 'yape';

const CheckoutPage: FC = () => {
  const [payment, setPayment] = useState<PaymentMethod>('card');
  const [formData, setFormData] = useState<CheckoutFormData>({
    fullName: '',
    phone: '',
    address: '',
    paymentMethod: 'card'
  });

  const handlePaymentChange = (method: PaymentMethod): void => {
    setPayment(method);
    setFormData(prev => ({ ...prev, paymentMethod: method }));
  };

  return (
    <div className="pt-28 pb-24 px-8 max-w-7xl mx-auto w-full animate-in fade-in duration-500">
      <div className="mb-12">
        <Link to="/menu" className="inline-flex items-center gap-2 text-on-surface-variant hover:text-primary font-bold text-sm mb-6">
          <ArrowLeft size={16} /> Volver al Resumen
        </Link>
        <h1 className="font-display text-4xl text-on-surface leading-tight">Completar Pedido</h1>
        <p className="text-on-surface-variant mt-1 text-lg">Finaliza tu compra de forma segura.</p>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-12 gap-12">
        {/* Forms */}
        <div className="lg:col-span-8 flex flex-col gap-8">
          <section className="bg-surface-container-lowest rounded-[2rem] border border-outline-variant/30 shadow-xl shadow-primary/5 p-10">
            <h2 className="font-display text-2xl text-on-surface flex items-center gap-3 mb-10">
              <User className="text-primary" /> Datos de Entrega
            </h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div className="flex flex-col gap-2">
                <label className="text-xs font-bold text-on-surface-variant uppercase tracking-widest">Nombre Completo</label>
                <input 
                  className="bg-surface border border-outline-variant rounded-xl px-4 py-3 focus:ring-2 focus:ring-primary/20 outline-none" 
                  placeholder="Ej. María Pérez"
                  type="text"
                  value={formData.fullName}
                  onChange={(e) => setFormData(prev => ({ ...prev, fullName: e.target.value }))}
                />
              </div>
              <div className="flex flex-col gap-2">
                <label className="text-xs font-bold text-on-surface-variant uppercase tracking-widest">Teléfono</label>
                <input 
                  className="bg-surface border border-outline-variant rounded-xl px-4 py-3 focus:ring-2 focus:ring-primary/20 outline-none" 
                  placeholder="+51 999 999 999"
                  type="tel"
                  value={formData.phone}
                  onChange={(e) => setFormData(prev => ({ ...prev, phone: e.target.value }))}
                />
              </div>
              <div className="flex flex-col gap-2 md:col-span-2">
                <label className="text-xs font-bold text-on-surface-variant uppercase tracking-widest">Dirección de Entrega</label>
                <div className="relative">
                  <MapPin className="absolute left-4 top-1/2 -translate-y-1/2 text-outline" size={18} />
                  <input 
                    className="w-full bg-surface border border-outline-variant rounded-xl px-12 py-3 focus:ring-2 focus:ring-primary/20 outline-none" 
                    placeholder="Calle, Avenida, Número, Distrito"
                    type="text"
                    value={formData.address}
                    onChange={(e) => setFormData(prev => ({ ...prev, address: e.target.value }))}
                  />
                </div>
              </div>
            </div>
          </section>

          <section className="bg-surface-container-lowest rounded-[2rem] border border-outline-variant/30 shadow-xl shadow-primary/5 p-10">
            <h2 className="font-display text-2xl text-on-surface flex items-center gap-3 mb-10">
              <CreditCard className="text-primary" /> Método de Pago
            </h2>
            <div className="flex flex-col gap-4">
              <div 
                onClick={() => handlePaymentChange('card')}
                className={cn(
                  "p-6 rounded-2xl border-2 transition-all cursor-pointer",
                  payment === 'card' ? "border-primary bg-primary/5 shadow-md" : "border-outline-variant/30 hover:border-primary/50"
                )}
              >
                <div className="flex justify-between items-center mb-6">
                  <span className="font-bold text-on-surface">Tarjeta de Crédito / Débito</span>
                  <CreditCard className={payment === 'card' ? "text-primary" : "text-outline"} />
                </div>
                {payment === 'card' && (
                  <div className="grid grid-cols-2 gap-4 animate-in slide-in-from-top-2 duration-300">
                    <input 
                      className="col-span-2 bg-surface border border-outline-variant rounded-xl px-4 py-3" 
                      placeholder="Número de Tarjeta"
                      type="text"
                    />
                    <input 
                      className="bg-surface border border-outline-variant rounded-xl px-4 py-3" 
                      placeholder="MM/AA"
                      type="text"
                    />
                    <input 
                      className="bg-surface border border-outline-variant rounded-xl px-4 py-3" 
                      placeholder="CVC"
                      type="text"
                    />
                    <input 
                      className="col-span-2 bg-surface border border-outline-variant rounded-xl px-4 py-3" 
                      placeholder="Titular de la Tarjeta"
                      type="text"
                    />
                  </div>
                )}
              </div>

              <div 
                onClick={() => handlePaymentChange('yape')}
                className={cn(
                  "p-6 rounded-2xl border-2 transition-all cursor-pointer flex justify-between items-center",
                  payment === 'yape' ? "border-primary bg-primary/5 shadow-md" : "border-outline-variant/30 hover:border-primary/50"
                )}
              >
                <span className="font-bold">Yape / Plin</span>
                <Smartphone className={payment === 'yape' ? "text-primary" : "text-outline"} />
              </div>
            </div>
          </section>
        </div>

        {/* Sidebar Summary */}
        <div className="lg:col-span-4">
          <div className="bg-surface-container-low rounded-[2rem] border border-outline-variant/30 shadow-xl shadow-primary/5 p-8 sticky top-28">
            <h3 className="font-display text-2xl text-on-surface mb-8">Resumen</h3>
            <div className="space-y-6 mb-10">
              <div className="flex gap-4">
                <img 
                  src="https://lh3.googleusercontent.com/aida-public/AB6AXuCo-qm8EdpFuD7AQGcXngiaxWbC3r4kxQMg0GPN6-FgCY8mvOXeuWa4GpukAQfz9BRfaAZ7L8h6WRU3anzwoN1IHTomAwILRF8_fJ2Ic9xZltdOlW_cJBLXfCP2ClHJuEH3fW_dB1HnO9FcDUiA1FrrFlsnN5pIJjukFH6K6bBLz7nOCEs46VEYn70BoE7Gc-qIZIVBS17yx0xY_Z9UXhoYSylEBLB2Pe4OvVWV_ENFJMBvD6aOTrJyBi4cdrx_COLw-KmeQV_kj7E" 
                  className="w-16 h-16 rounded-xl object-cover border border-outline-variant/20" 
                  alt="Item" 
                />
                <div className="flex-grow">
                  <div className="flex justify-between font-bold text-sm">
                    <span>Ensalada Andina</span>
                    <span>S/ 24.00</span>
                  </div>
                  <span className="text-xs text-on-surface-variant font-semibold">Cant: 1</span>
                </div>
              </div>
            </div>

            <div className="space-y-3 mb-8 border-t border-outline-variant/30 pt-6">
              <div className="flex justify-between text-sm font-semibold text-on-surface-variant">
                <span>Subtotal</span>
                <span>S/ 24.00</span>
              </div>
              <div className="flex justify-between text-sm font-semibold text-on-surface-variant">
                <span>Envío</span>
                <span>S/ 8.00</span>
              </div>
              <div className="flex justify-between items-center pt-2">
                <span className="font-display text-2xl font-bold">Total</span>
                <span className="font-display text-2xl font-bold text-primary">S/ 32.00</span>
              </div>
            </div>

            <Link to="/confirmation" className="w-full bg-primary text-on-primary font-bold py-4 rounded-full flex items-center justify-center gap-2 shadow-lg shadow-primary/20">
              <Lock size={18} /> Confirmar Pago
            </Link>
            
            <div className="mt-6 flex items-center justify-center gap-2 text-[10px] font-bold text-on-surface-variant uppercase tracking-widest leading-none">
              <ShieldCheck size={14} className="text-primary" /> Pago 100% seguro y encriptado
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
