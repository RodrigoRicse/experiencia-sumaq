import Sidebar from '../components/Sidebar';
import { 
  TrendingUp, 
  TrendingDown, 
  Receipt, 
  Timer, 
  CircleDollarSign, 
  Search, 
  Bell, 
  ChevronDown
} from 'lucide-react';
import { 
  BarChart, 
  Bar, 
  XAxis, 
  YAxis, 
  CartesianGrid, 
  Tooltip, 
  ResponsiveContainer,
  Cell
} from 'recharts';
import { FC } from 'react';

interface ChartDataPoint {
  name: string;
  value: number;
  color: string;
}

const data: ChartDataPoint[] = [
  { name: 'Lun', value: 40, color: '#3e521933' },
  { name: 'Mar', value: 55, color: '#3e52194d' },
  { name: 'Mie', value: 45, color: '#3e521933' },
  { name: 'Jue', value: 85, color: '#3e5219' },
  { name: 'Vie', value: 70, color: '#3e521999' },
  { name: 'Sab', value: 95, color: '#3e5219cc' },
  { name: 'Dom', value: 60, color: '#3e521966' },
];

const AdminDashboard: FC = () => {
  return (
    <div className="flex min-h-screen bg-background">
      <Sidebar />
      <main className="flex-1 ml-64 p-12 bg-background/50">
        <header className="flex justify-between items-end mb-16">
          <div>
            <h2 className="font-display text-4xl text-on-surface mb-1">Vista General</h2>
            <p className="text-on-surface-variant">Resumen de operaciones del día.</p>
          </div>
          <div className="flex items-center gap-6">
            <div className="relative">
              <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-outline" size={18} />
              <input 
                type="text" 
                placeholder="Buscar pedidos..." 
                className="pl-10 pr-4 py-2 rounded-xl border border-outline-variant bg-surface focus:border-primary focus:ring-2 focus:ring-primary/20 outline-none w-64 transition-all"
              />
            </div>
            <button className="p-2 rounded-xl hover:bg-surface-variant transition-colors text-on-surface-variant relative">
              <Bell size={22} />
              <span className="absolute top-2 right-2 w-2 h-2 bg-secondary rounded-full"></span>
            </button>
            <div className="w-10 h-10 rounded-full border-2 border-primary-fixed-dim shrink-0 bg-surface overflow-hidden">
              <img 
                src="https://lh3.googleusercontent.com/aida-public/AB6AXuBF75ZbdU_oa-lycvvnk1hRG3NAKXpP6dbC3qb5dBblzLwsnifSj_MjCndomlVqY9lWv5IopJLqCkb-tSurK9duxAt1HMffWaOPwvf77v8rWtAKcQzL4F0OLUGXVCn0JuAaYb1QYemVh09uJO6uNSwDzkXOkxGPdiB0zioaT_fv89X3Oj5rsrOqum35Vm3h02dlxXHpxwA6orngelZbk6HkJU3XRzY3A_fsfdcGIukbWpxjHSIbtA_PRsFvVqf0ageLhjyj7qrxQkA" 
                alt="Admin" 
                className="w-full h-full object-cover"
              />
            </div>
          </div>
        </header>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-12">
          {/* Metrics */}
          <div className="glass-card rounded-2xl p-8 flex flex-col justify-between h-48">
            <div className="flex justify-between items-start">
              <div className="p-3 bg-primary-container/20 rounded-xl text-primary">
                <CircleDollarSign size={24} />
              </div>
              <span className="bg-primary/10 text-primary px-3 py-1 rounded-full text-xs font-bold flex items-center gap-1">
                <TrendingUp size={14} /> +12.5%
              </span>
            </div>
            <div>
              <p className="text-sm font-semibold text-on-surface-variant mb-1">Ventas Hoy</p>
              <h3 className="font-display text-4xl text-on-surface">S/ 4,250</h3>
            </div>
          </div>

          <div className="glass-card rounded-2xl p-8 flex flex-col justify-between h-48">
            <div className="flex justify-between items-start">
              <div className="p-3 bg-secondary-container/20 rounded-xl text-secondary">
                <Receipt size={24} />
              </div>
              <span className="bg-primary/10 text-primary px-3 py-1 rounded-full text-xs font-bold flex items-center gap-1">
                <TrendingUp size={14} /> +5.2%
              </span>
            </div>
            <div>
              <p className="text-sm font-semibold text-on-surface-variant mb-1">Pedidos Totales</p>
              <h3 className="font-display text-4xl text-on-surface">142</h3>
            </div>
          </div>

          <div className="glass-card rounded-2xl p-8 flex flex-col justify-between h-48">
            <div className="flex justify-between items-start">
              <div className="p-3 bg-tertiary-container/20 rounded-xl text-tertiary">
                <Timer size={24} />
              </div>
              <span className="bg-secondary/10 text-secondary px-3 py-1 rounded-full text-xs font-bold flex items-center gap-1">
                <TrendingDown size={14} /> -2 min
              </span>
            </div>
            <div>
              <p className="text-sm font-semibold text-on-surface-variant mb-1">T. Promedio Refugio</p>
              <h3 className="font-display text-4xl text-on-surface">18<span className="text-xl text-on-surface-variant ml-1 uppercase">min</span></h3>
            </div>
          </div>
        </div>

        <div className="grid grid-cols-12 gap-6">
          <div className="col-span-12 lg:col-span-8 glass-card rounded-2xl p-8 h-[500px] flex flex-col">
            <div className="flex justify-between items-center mb-8">
              <h3 className="font-display text-2xl text-on-surface">Ingresos Semanales</h3>
              <div className="flex items-center gap-2 bg-surface border border-outline-variant rounded-xl px-4 py-2 text-sm font-semibold cursor-pointer">
                Esta Semana <ChevronDown size={16} />
              </div>
            </div>
            <div className="flex-1 w-full">
              <ResponsiveContainer width="100%" height="100%">
                <BarChart data={data} margin={{ top: 20, right: 0, left: -20, bottom: 0 }}>
                  <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="#c5c8b855" />
                  <XAxis 
                    dataKey="name" 
                    axisLine={false} 
                    tickLine={false} 
                    tick={{ fill: '#45483c', fontSize: 12, fontWeight: 600 } as any}
                    dy={10}
                  />
                  <YAxis 
                    axisLine={false} 
                    tickLine={false} 
                    tick={{ fill: '#45483c', fontSize: 12, fontWeight: 600 } as any}
                  />
                  <Tooltip 
                    cursor={{ fill: 'transparent' }}
                    contentStyle={{ 
                      borderRadius: '12px', 
                      backgroundColor: '#fbfbe2', 
                      border: '1px solid #c5c8b8',
                      boxShadow: '0 4px 20px rgba(0,0,0,0.05)'
                    } as any}
                  />
                  <Bar dataKey="value" radius={[4, 4, 0, 0]} barSize={40}>
                    {data.map((entry: ChartDataPoint, index: number) => (
                      <Cell key={`cell-${index}`} fill={entry.color} />
                    ))}
                  </Bar>
                </BarChart>
              </ResponsiveContainer>
            </div>
          </div>

          <div className="col-span-12 lg:col-span-4 flex flex-col gap-6">
            <div className="glass-card rounded-2xl p-8 flex-1">
              <h3 className="font-display text-2xl text-on-surface mb-6">Platos Estrella</h3>
              <div className="space-y-4">
                {[
                  { name: 'Ceviche Andino', orders: 42, price: '1,470', img: 'https://lh3.googleusercontent.com/aida-public/AB6AXuDCMjr_1s1BpsMBLVZL-Y2DA7b1rqUI3g4kcJwwhjatr4LsnGv0KbQIxk4DrOnZ7ESQhNP7LAmldPVJpBbZtNO5jfSwRThWziI4KFpCNcAVd83VW8ebM46lS2qwmiubo7Ts15zzVBV98QjJNJ18CwNMzIeRS48iNk4oMrTJWfhgnzMmJUFBMUCsu8uX_E0tyKc8LDUW7wTTSaZrB_ahkHtqUImmXa-jLrASVzKnuCF0x_t78KpoZ_wZpg9nzm47ONP1YJbeMl57o0w' },
                  { name: 'Quinotto de Setas', orders: 28, price: '896', img: 'https://lh3.googleusercontent.com/aida-public/AB6AXuA-NzvCWxsFbClcgNV2j9xqmmOIT4qHpMEhFdOEoQhupE2VRCYFyhIuKzgjEInlff4Tvwn_dlG80n18sUgPboYINhqvetZGVMZ72t0VLWxfnUxMvU9yu1RQnoRvG3z7eELvBNX7fM68jp81PTBEdMRdWlRGGksSrGsXtAAGogNrlJt_hRWzACYaDUi5COt6gb0QxhYGtyYAMPEPttK0A1VCYh8LGsFMU-zS01bWQof12YZtpTMwP27DDWhsnNE5f_CD4LjN_8lOBxk' }
                ].map((item) => (
                  <div key={item.name} className="flex items-center justify-between p-2 hover:bg-surface-variant/30 rounded-xl transition-colors cursor-pointer group">
                    <div className="flex items-center gap-4">
                      <img src={item.img} alt={item.name} className="w-12 h-12 rounded-lg object-cover border border-outline-variant" />
                      <div>
                        <p className="font-semibold text-on-surface text-sm">{item.name}</p>
                        <p className="text-xs text-on-surface-variant">{item.orders} pedidos</p>
                      </div>
                    </div>
                    <span className="font-display font-bold text-primary">S/ {item.price}</span>
                  </div>
                ))}
              </div>
            </div>

            <div className="glass-card rounded-2xl p-8 flex-1">
              <h3 className="font-display text-2xl text-on-surface mb-6">Actividad Reciente</h3>
              <div className="relative border-l-2 border-outline-variant/30 ml-2 pl-6 space-y-8">
                {[
                  { title: 'Nuevo pedido #4029', desc: 'Hace 2 min • Mesa 4', color: 'bg-primary' },
                  { title: 'Alerta de inventario', desc: 'Hace 15 min • Palta Hass baja', color: 'bg-secondary' },
                  { title: 'Cierre de turno mañana', desc: 'Hace 1 hr • Usuario: Ana', color: 'bg-tertiary-container' }
                ].map((item, idx) => (
                  <div key={idx} className="relative">
                    <span className={`absolute -left-[33px] top-1 w-3 h-3 ${item.color} rounded-full ring-4 ring-surface`}></span>
                    <p className="font-semibold text-on-surface text-sm">{item.title}</p>
                    <p className="text-xs text-on-surface-variant">{item.desc}</p>
                  </div>
                ))}
              </div>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
};

export default AdminDashboard;
          <div>
            <h2 className="font-display text-4xl text-on-surface mb-1">Vista General</h2>
            <p className="text-on-surface-variant">Resumen de operaciones del día.</p>
          </div>
          <div className="flex items-center gap-6">
            <div className="relative">
              <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-outline" size={18} />
              <input 
                type="text" 
                placeholder="Buscar pedidos..." 
                className="pl-10 pr-4 py-2 rounded-xl border border-outline-variant bg-surface focus:border-primary focus:ring-2 focus:ring-primary/20 outline-none w-64 transition-all"
              />
            </div>
            <button className="p-2 rounded-xl hover:bg-surface-variant transition-colors text-on-surface-variant relative">
              <Bell size={22} />
              <span className="absolute top-2 right-2 w-2 h-2 bg-secondary rounded-full"></span>
            </button>
            <div className="w-10 h-10 rounded-full border-2 border-primary-fixed-dim shrink-0 bg-surface overflow-hidden">
              <img 
                src="https://lh3.googleusercontent.com/aida-public/AB6AXuBF75ZbdU_oa-lycvvnk1hRG3NAKXpP6dbC3qb5dBblzLwsnifSj_MjCndomlVqY9lWv5IopJLqCkb-tSurK9duxAt1HMffWaOPwvf77v8rWtAKcQzL4F0OLUGXVCn0JuAaYb1QYemVh09uJO6uNSwDzkXOkxGPdiB0zioaT_fv89X3Oj5rsrOqum35Vm3h02dlxXHpxwA6orngelZbk6HkJU3XRzY3A_fsfdcGIukbWpxjHSIbtA_PRsFvVqf0ageLhjyj7qrxQkA" 
                alt="Admin" 
                className="w-full h-full object-cover"
              />
            </div>
          </div>
        </header>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-12">
          {/* Metrics */}
          <div className="glass-card rounded-2xl p-8 flex flex-col justify-between h-48">
            <div className="flex justify-between items-start">
              <div className="p-3 bg-primary-container/20 rounded-xl text-primary">
                <CircleDollarSign size={24} />
              </div>
              <span className="bg-primary/10 text-primary px-3 py-1 rounded-full text-xs font-bold flex items-center gap-1">
                <TrendingUp size={14} /> +12.5%
              </span>
            </div>
            <div>
              <p className="text-sm font-semibold text-on-surface-variant mb-1">Ventas Hoy</p>
              <h3 className="font-display text-4xl text-on-surface">S/ 4,250</h3>
            </div>
          </div>

          <div className="glass-card rounded-2xl p-8 flex flex-col justify-between h-48">
            <div className="flex justify-between items-start">
              <div className="p-3 bg-secondary-container/20 rounded-xl text-secondary">
                <Receipt size={24} />
              </div>
              <span className="bg-primary/10 text-primary px-3 py-1 rounded-full text-xs font-bold flex items-center gap-1">
                <TrendingUp size={14} /> +5.2%
              </span>
            </div>
            <div>
              <p className="text-sm font-semibold text-on-surface-variant mb-1">Pedidos Totales</p>
              <h3 className="font-display text-4xl text-on-surface">142</h3>
            </div>
          </div>

          <div className="glass-card rounded-2xl p-8 flex flex-col justify-between h-48">
            <div className="flex justify-between items-start">
              <div className="p-3 bg-tertiary-container/20 rounded-xl text-tertiary">
                <Timer size={24} />
              </div>
              <span className="bg-secondary/10 text-secondary px-3 py-1 rounded-full text-xs font-bold flex items-center gap-1">
                <TrendingDown size={14} /> -2 min
              </span>
            </div>
            <div>
              <p className="text-sm font-semibold text-on-surface-variant mb-1">T. Promedio Refugio</p>
              <h3 className="font-display text-4xl text-on-surface">18<span className="text-xl text-on-surface-variant ml-1 uppercase">min</span></h3>
            </div>
          </div>
        </div>

        <div className="grid grid-cols-12 gap-6">
          <div className="col-span-12 lg:col-span-8 glass-card rounded-2xl p-8 h-[500px] flex flex-col">
            <div className="flex justify-between items-center mb-8">
              <h3 className="font-display text-2xl text-on-surface">Ingresos Semanales</h3>
              <div className="flex items-center gap-2 bg-surface border border-outline-variant rounded-xl px-4 py-2 text-sm font-semibold cursor-pointer">
                Esta Semana <ChevronDown size={16} />
              </div>
            </div>
            <div className="flex-1 w-full">
              <ResponsiveContainer width="100%" height="100%">
                <BarChart data={data} margin={{ top: 20, right: 0, left: -20, bottom: 0 }}>
                  <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="#c5c8b855" />
                  <XAxis 
                    dataKey="name" 
                    axisLine={false} 
                    tickLine={false} 
                    tick={{ fill: '#45483c', fontSize: 12, fontWeight: 600 }}
                    dy={10}
                  />
                  <YAxis 
                    axisLine={false} 
                    tickLine={false} 
                    tick={{ fill: '#45483c', fontSize: 12, fontWeight: 600 }}
                  />
                  <Tooltip 
                    cursor={{ fill: 'transparent' }}
                    contentStyle={{ 
                      borderRadius: '12px', 
                      backgroundColor: '#fbfbe2', 
                      border: '1px solid #c5c8b8',
                      boxShadow: '0 4px 20px rgba(0,0,0,0.05)'
                    }}
                  />
                  <Bar dataKey="value" radius={[4, 4, 0, 0]} barSize={40}>
                    {data.map((entry, index) => (
                      <Cell key={`cell-${index}`} fill={entry.color} />
                    ))}
                  </Bar>
                </BarChart>
              </ResponsiveContainer>
            </div>
          </div>

          <div className="col-span-12 lg:col-span-4 flex flex-col gap-6">
            <div className="glass-card rounded-2xl p-8 flex-1">
              <h3 className="font-display text-2xl text-on-surface mb-6">Platos Estrella</h3>
              <div className="space-y-4">
                {[
                  { name: 'Ceviche Andino', orders: 42, price: '1,470', img: 'https://lh3.googleusercontent.com/aida-public/AB6AXuDCMjr_1s1BpsMBLVZL-Y2DA7b1rqUI3g4kcJwwhjatr4LsnGv0KbQIxk4DrOnZ7ESQhNP7LAmldPVJpBbZtNO5jfSwRThWziI4KFpCNcAVd83VW8ebM46lS2qwmiubo7Ts15zzVBV98QjJNJ18CwNMzIeRS48iNk4oMrTJWfhgnzMmJUFBMUCsu8uX_E0tyKc8LDUW7wTTSaZrB_ahkHtqUImmXa-jLrASVzKnuCF0x_t78KpoZ_wZpg9nzm47ONP1YJbeMl57o0w' },
                  { name: 'Quinotto de Setas', orders: 28, price: '896', img: 'https://lh3.googleusercontent.com/aida-public/AB6AXuA-NzvCWxsFbClcgNV2j9xqmmOIT4qHpMEhFdOEoQhupE2VRCYFyhIuKzgjEInlff4Tvwn_dlG80n18sUgPboYINhqvetZGVMZ72t0VLWxfnUxMvU9yu1RQnoRvG3z7eELvBNX7fM68jp81PTBEdMRdWlRGGksSrGsXtAAGogNrlJt_hRWzACYaDUi5COt6gb0QxhYGtyYAMPEPttK0A1VCYh8LGsFMU-zS01bWQof12YZtpTMwP27DDWhsnNE5f_CD4LjN_8lOBxk' }
                ].map((item) => (
                  <div key={item.name} className="flex items-center justify-between p-2 hover:bg-surface-variant/30 rounded-xl transition-colors cursor-pointer group">
                    <div className="flex items-center gap-4">
                      <img src={item.img} alt={item.name} className="w-12 h-12 rounded-lg object-cover border border-outline-variant" />
                      <div>
                        <p className="font-semibold text-on-surface text-sm">{item.name}</p>
                        <p className="text-xs text-on-surface-variant">{item.orders} pedidos</p>
                      </div>
                    </div>
                    <span className="font-display font-bold text-primary">S/ {item.price}</span>
                  </div>
                ))}
              </div>
            </div>

            <div className="glass-card rounded-2xl p-8 flex-1">
              <h3 className="font-display text-2xl text-on-surface mb-6">Actividad Reciente</h3>
              <div className="relative border-l-2 border-outline-variant/30 ml-2 pl-6 space-y-8">
                {[
                  { title: 'Nuevo pedido #4029', desc: 'Hace 2 min • Mesa 4', color: 'bg-primary' },
                  { title: 'Alerta de inventario', desc: 'Hace 15 min • Palta Hass baja', color: 'bg-secondary' },
                  { title: 'Cierre de turno mañana', desc: 'Hace 1 hr • Usuario: Ana', color: 'bg-tertiary-container' }
                ].map((item, idx) => (
                  <div key={idx} className="relative">
                    <span className={`absolute -left-[33px] top-1 w-3 h-3 ${item.color} rounded-full ring-4 ring-surface`}></span>
                    <p className="font-semibold text-on-surface text-sm">{item.title}</p>
                    <p className="text-xs text-on-surface-variant">{item.desc}</p>
                  </div>
                ))}
              </div>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}
