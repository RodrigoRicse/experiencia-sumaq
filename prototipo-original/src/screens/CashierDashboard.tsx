import Sidebar from '../components/Sidebar';
import { 
  Plus, 
  Filter, 
  Search, 
  ChevronLeft, 
  ChevronRight, 
  CreditCard, 
  Banknote, 
  Bike,
  CheckCircle2,
  AlertCircle
} from 'lucide-react';
import { FC } from 'react';

const CashierDashboard: FC = () => {
  return (
    <div className="flex min-h-screen bg-background">
      <Sidebar />
      <main className="flex-1 ml-64 p-12 max-w-7xl mx-auto w-full">
        <header className="flex flex-col md:flex-row md:items-center justify-between gap-6 mb-12">
          <div>
            <h2 className="font-display text-4xl text-on-surface leading-tight">Caja y Entregas</h2>
            <p className="text-on-surface-variant mt-1">Gestiona pagos pendientes y confirma entregas de pedidos.</p>
          </div>
          <div className="flex items-center gap-4">
            <button className="bg-surface-container-high text-on-surface font-semibold py-2 px-4 rounded-xl border border-outline-variant/50 hover:bg-surface-container-highest transition-colors flex items-center gap-2">
              <Filter size={18} /> Filtrar
            </button>
            <div className="bg-surface-container text-on-surface flex rounded-xl p-1 border border-outline-variant/30">
              <button className="bg-surface text-primary font-bold py-2 px-4 rounded-lg shadow-sm">Todos</button>
              <button className="text-on-surface-variant hover:text-on-surface font-bold py-2 px-4 rounded-lg transition-colors">Pendiente Pago</button>
              <button className="text-on-surface-variant hover:text-on-surface font-bold py-2 px-4 rounded-lg transition-colors">Para Entregar</button>
            </div>
          </div>
        </header>

        <div className="grid grid-cols-1 xl:grid-cols-3 gap-6">
          {/* Summary */}
          <div className="xl:col-span-1 bg-surface-container-low rounded-[2rem] border border-outline-variant/30 p-8 shadow-xl shadow-primary/5 flex flex-col gap-6">
            <h3 className="font-display text-2xl text-on-surface tracking-tight">Resumen de Turno</h3>
            <div className="grid grid-cols-2 gap-4">
              <div className="bg-surface rounded-2xl p-6 border border-outline-variant/20 flex flex-col items-center text-center">
                <Banknote className="text-primary mb-2" size={24} />
                <p className="text-[10px] font-bold text-on-surface-variant uppercase tracking-widest">Ventas Hoy</p>
                <p className="font-display text-xl text-primary mt-1">S/ 1,245.00</p>
              </div>
              <div className="bg-surface rounded-2xl p-6 border border-outline-variant/20 flex flex-col items-center text-center">
                <Bike className="text-tertiary mb-2" size={24} />
                <p className="text-[10px] font-bold text-on-surface-variant uppercase tracking-widest">Pendientes</p>
                <p className="font-display text-xl text-tertiary mt-1">8 Pedidos</p>
              </div>
            </div>
            <button className="w-full bg-secondary text-on-secondary font-bold py-3 px-6 rounded-2xl shadow-md hover:opacity-90 transition-opacity mt-auto">
              Cerrar Turno
            </button>
          </div>

          {/* Table */}
          <div className="xl:col-span-2 bg-surface-container-lowest rounded-[2rem] border border-outline-variant/30 shadow-xl shadow-primary/5 overflow-hidden flex flex-col">
            <div className="p-6 border-b border-outline-variant/30 flex justify-between items-center bg-surface-container-low/50">
              <h3 className="font-display text-2xl text-on-surface tracking-tight">Pedidos Activos</h3>
              <div className="relative">
                <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant" size={18} />
                <input 
                  type="text" 
                  placeholder="Buscar ID o Cliente..." 
                  className="pl-10 pr-4 py-2 bg-surface border border-outline-variant rounded-xl font-sans text-sm focus:outline-none focus:ring-2 focus:ring-primary w-64"
                />
              </div>
            </div>
            <div className="overflow-x-auto">
              <table className="w-full text-left">
                <thead>
                  <tr className="bg-surface-container-low/30 border-b border-outline-variant/30">
                    <th className="py-4 px-6 text-xs font-bold text-on-surface-variant uppercase tracking-widest">ID Pedido</th>
                    <th className="py-4 px-6 text-xs font-bold text-on-surface-variant uppercase tracking-widest">Cliente</th>
                    <th className="py-4 px-6 text-xs font-bold text-on-surface-variant uppercase tracking-widest">Monto</th>
                    <th className="py-4 px-6 text-xs font-bold text-on-surface-variant uppercase tracking-widest">Estado</th>
                    <th className="py-4 px-6 text-xs font-bold text-on-surface-variant uppercase tracking-widest text-right">Acción</th>
                  </tr>
                </thead>
                <tbody className="text-sm divide-y divide-outline-variant/20 font-sans">
                  <tr className="hover:bg-surface-container-low/50 transition-colors">
                    <td className="py-6 px-6 font-bold text-on-surface">#ORD-0042</td>
                    <td className="py-6 px-6">
                      <div className="flex items-center gap-3">
                        <div className="w-8 h-8 rounded-full bg-primary-container text-on-primary-container flex items-center justify-center font-bold text-xs">MP</div>
                        <span className="font-semibold">María Pérez</span>
                      </div>
                    </td>
                    <td className="py-6 px-6 font-bold">S/ 85.50</td>
                    <td className="py-6 px-6">
                      <div className="flex flex-col gap-1">
                        <span className="inline-flex items-center gap-1 px-2 py-1 rounded-full bg-surface-variant text-on-surface-variant text-[10px] font-bold w-max">
                          <CreditCard size={12} /> TARJETA
                        </span>
                        <span className="inline-flex items-center gap-1 px-2 py-1 rounded-full bg-error-container text-on-error-container text-[10px] font-bold w-max">
                          <AlertCircle size={12} /> POR COBRAR
                        </span>
                      </div>
                    </td>
                    <td className="py-6 px-6 text-right">
                      <button className="bg-primary text-on-primary text-xs font-bold py-2 px-6 rounded-full shadow-sm hover:translate-y-[-1px] transition-all">
                        Cobrar
                      </button>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div className="p-6 border-t border-outline-variant/30 flex items-center justify-between mt-auto">
              <p className="text-xs font-medium text-on-surface-variant">Mostrando 1 a 3 de 12 pedidos</p>
              <div className="flex gap-2">
                <button className="p-2 rounded-lg border border-outline-variant/50 text-on-surface-variant hover:bg-surface-variant transition-colors"><ChevronLeft size={16}/></button>
                <button className="p-2 rounded-lg border border-outline-variant/50 text-on-surface-variant hover:bg-surface-variant transition-colors"><ChevronRight size={16}/></button>
              </div>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}
