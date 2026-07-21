import Sidebar from '../components/Sidebar';
import { 
  Timer, 
  Filter, 
  MoreVertical, 
  Clock, 
  AlertCircle, 
  CheckCircle2, 
  Circle,
  RotateCw,
  ShoppingBag,
  Soup,
  LucideIcon
} from 'lucide-react';
import { ORDERS } from '../constants';
import { Order, OrderItem } from '../types';
import { FC } from 'react';

const KitchenDashboard: FC = () => {
  const pendingOrders: Order[] = ORDERS.filter(o => o.status === 'pending');

  return (
    <div className="flex h-screen bg-background overflow-hidden font-sans">
      <Sidebar />
      <main className="flex-1 ml-64 flex flex-col pt-0">
        <header className="px-10 py-8 flex justify-between items-center border-b border-outline-variant/30 bg-surface/50 backdrop-blur-md z-10">
          <div>
            <h2 className="font-display text-4xl text-primary">Estación Principal</h2>
            <p className="text-on-surface-variant mt-1">Monitor de pedidos en vivo · {pendingOrders.length} tickets activos</p>
          </div>
          <div className="flex items-center gap-6">
            <div className="flex items-center gap-3 bg-surface-container-high px-6 py-2 rounded-full border border-outline-variant/50">
              <Clock className="text-tertiary" size={20} />
              <span className="font-semibold text-on-surface text-sm">T. Promedio: 14 min</span>
            </div>
            <button className="p-2 text-on-surface-variant hover:bg-surface-variant rounded-full transition-colors">
              <Filter size={22} />
            </button>
            <button className="p-2 text-on-surface-variant hover:bg-surface-variant rounded-full transition-colors">
              <MoreVertical size={22} />
            </button>
          </div>
        </header>

        <div className="flex-1 p-6 grid grid-cols-3 gap-6 overflow-hidden">
          {/* Column 1: Pendientes */}
          <div className="flex flex-col h-full bg-surface-container-low/60 rounded-[2rem] border border-outline-variant/30 shadow-sm overflow-hidden">
            <div className="p-6 border-b border-outline-variant/20 flex justify-between items-center bg-surface-container-low">
              <h3 className="font-display text-lg text-on-surface flex items-center gap-2">
                <span className="w-3 h-3 rounded-full bg-secondary block"></span>
                Pendientes
              </h3>
              <span className="bg-surface-variant text-on-surface-variant text-xs font-bold px-3 py-1 rounded-full">4</span>
            </div>
            <div className="flex-1 overflow-y-auto p-4 flex flex-col gap-6 custom-scrollbar">
              {pendingOrders.map((order: Order) => (
                <article key={order.id} className="bg-surface rounded-3xl p-6 shadow-md border-l-4 border-l-secondary border border-outline-variant/20 flex flex-col gap-6 hover:translate-y-[-2px] transition-transform">
                  <div className="flex justify-between items-start">
                    <div>
                      <span className="font-bold text-on-surface">Orden #{order.id}</span>
                      <p className="text-xs text-on-surface-variant">
                        {order.type === 'dine-in' ? `Mesa ${order.table} · Mozo: ${order.waiter}` : 'Para Llevar · App'}
                      </p>
                    </div>
                    <div className={order.id === '1045' ? "flex items-center gap-1 text-secondary bg-secondary-container/20 px-3 py-1 rounded-lg" : "flex items-center gap-1 text-primary bg-primary-container/10 px-3 py-1 rounded-lg"}>
                      <Timer size={14} />
                      <span className="text-xs font-bold">{order.time}</span>
                    </div>
                  </div>
                  <ul className="flex flex-col gap-3 border-y border-outline-variant/10 py-4">
                    {order.items.map((item: OrderItem, idx: number) => (
                      <div key={idx}>
                        <li className="font-semibold text-on-surface text-sm">{item.quantity}x {item.name}</li>
                        {item.notes && (
                          <div className="bg-error-container/20 p-2 rounded-lg flex items-start gap-2 mt-2">
                            <AlertCircle className="text-error shrink-0" size={14} />
                            <p className="text-xs text-error font-medium">{item.notes}</p>
                          </div>
                        )}
                      </div>
                    ))}
                  </ul>
                  <button className="w-full bg-primary text-on-primary font-bold py-3 rounded-full hover:bg-primary/90 transition-colors">
                    Iniciar Preparación
                  </button>
                </article>
              ))}
            </div>
          </div>

          {/* Column 2: En Preparación */}
          <div className="flex flex-col h-full bg-surface-container-low/60 rounded-[2rem] border border-outline-variant/30 shadow-sm overflow-hidden">
            <div className="p-6 border-b border-outline-variant/20 flex justify-between items-center bg-surface-container-low">
              <h3 className="font-display text-lg text-on-surface flex items-center gap-2">
                <span className="w-3 h-3 rounded-full bg-tertiary block animate-pulse"></span>
                En Preparación
              </h3>
              <span className="bg-surface-variant text-on-surface-variant text-xs font-bold px-3 py-1 rounded-full">3</span>
            </div>
            <div className="flex-1 overflow-y-auto p-4 flex flex-col gap-6 custom-scrollbar">
              <article className="bg-surface rounded-3xl p-6 shadow-md border-l-4 border-l-tertiary border border-outline-variant/20 flex flex-col gap-6">
                <div className="flex justify-between items-start">
                  <div>
                    <span className="font-bold text-on-surface">Orden #1041</span>
                    <p className="text-xs text-on-surface-variant">Mesa 12 · Mozo: Carlos</p>
                  </div>
                  <div className="flex items-center gap-1 text-tertiary bg-tertiary-container/20 px-3 py-1 rounded-lg">
                    <RotateCw size={14} className="animate-spin" style={{ animationDuration: '3s' }} />
                    <span className="text-xs font-bold">14:20</span>
                  </div>
                </div>
                <ul className="flex flex-col gap-3 border-y border-outline-variant/10 py-4">
                  <li className="flex items-center gap-2 font-semibold text-on-surface text-sm">
                    <CheckCircle2 size={18} className="text-primary" />
                    <span className="line-through text-outline">1x Ceviche Clásico</span>
                  </li>
                  <li className="flex items-center gap-2 font-semibold text-on-surface text-sm">
                    <Circle size={18} className="text-outline" />
                    <span>1x Pachamanca a la Olla</span>
                  </li>
                </ul>
                <button className="w-full bg-transparent border-2 border-tertiary text-tertiary font-bold py-3 rounded-full hover:bg-tertiary/10 transition-colors">
                  Marcar Listo
                </button>
              </article>
            </div>
          </div>

          {/* Column 3: Listos */}
          <div className="flex flex-col h-full bg-surface-container-low/60 rounded-[2rem] border border-outline-variant/30 shadow-sm overflow-hidden opacity-95">
            <div className="p-6 border-b border-outline-variant/20 flex justify-between items-center bg-surface-container-low">
              <h3 className="font-display text-lg text-on-surface flex items-center gap-2">
                <span className="w-3 h-3 rounded-full bg-primary block"></span>
                Listos para Servir
              </h3>
              <span className="bg-surface-variant text-on-surface-variant text-xs font-bold px-3 py-1 rounded-full">2</span>
            </div>
            <div className="flex-1 overflow-y-auto p-4 flex flex-col gap-6 custom-scrollbar">
              <article className="bg-surface/80 backdrop-blur-sm rounded-3xl p-6 border border-primary/30 flex flex-col gap-6">
                <div className="flex justify-between items-start">
                  <div>
                    <span className="font-bold text-on-surface">Orden #1035</span>
                    <p className="text-xs text-on-surface-variant">Mesa 02</p>
                  </div>
                  <CheckCircle2 className="text-primary fill-primary/10" size={24} />
                </div>
                <ul className="flex flex-col gap-2 border-t border-outline-variant/10 pt-4 opacity-80">
                  <li className="text-xs font-semibold text-on-surface-variant italic">1x Papa a la Huancaína</li>
                  <li className="text-xs font-semibold text-on-surface-variant italic">2x Pisco Sour</li>
                </ul>
                <button className="w-full bg-surface-variant text-on-surface-variant font-bold py-3 rounded-full flex items-center justify-center gap-2 hover:bg-outline-variant/50 transition-colors">
                  <Soup size={18} />
                  Despachar
                </button>
              </article>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}
