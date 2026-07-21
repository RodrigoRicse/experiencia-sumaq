import Sidebar from '../components/Sidebar';
import { 
  Calendar, 
  Download, 
  TrendingUp, 
  Package,
  AlertTriangle,
  Search,
  MoreVertical,
  Edit2
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
import { ChartDataPoint } from '../types';

const chartData: ChartDataPoint[] = [
  { name: 'Entradas', value: 12000, margin: 4000, color: '#3e5219' },
  { name: 'Fondos', value: 14500, margin: 5000, color: '#556b2f' },
  { name: 'Postres', value: 6000, margin: 2500, color: '#735c00' },
  { name: 'Bebidas', value: 9000, margin: 3000, color: '#9f402d' },
  { name: 'Otros', value: 1500, margin: 500, color: '#75796b' },
];

const ReportsDashboard: FC = () => {
  return (
    <div className="flex min-h-screen bg-background font-sans">
      <Sidebar />
      <main className="flex-1 ml-64 p-12 max-w-7xl mx-auto w-full">
        <header className="flex justify-between items-end mb-12 pb-6 border-b border-outline-variant/30">
          <div>
            <h1 className="font-display text-4xl text-primary tracking-tight">Reportes e Inventario</h1>
            <p className="text-on-surface-variant mt-1.5">Análisis de rentabilidad y gestión de disponibilidad.</p>
          </div>
          <div className="flex items-center gap-4">
            <div className="flex items-center bg-surface border border-outline-variant rounded-full px-5 py-2.5 shadow-sm cursor-pointer">
              <Calendar className="text-on-surface-variant mr-3" size={18} />
              <span className="text-sm font-semibold">Este Mes (Octubre)</span>
            </div>
            <button className="flex items-center gap-2 bg-surface border border-outline-variant text-on-surface font-semibold px-5 py-2.5 rounded-full shadow-sm hover:bg-surface-variant transition-colors">
              <Download size={18} /> Exportar
            </button>
          </div>
        </header>

        <div className="grid grid-cols-12 gap-6">
          <div className="col-span-12 lg:col-span-4 flex flex-col gap-6">
            <div className="bg-surface-container-lowest rounded-[2rem] border border-outline-variant/30 p-8 shadow-xl shadow-primary/5">
              <div className="flex justify-between items-start mb-2">
                <h3 className="text-xs font-bold text-on-surface-variant uppercase tracking-widest">Ingresos Totales</h3>
                <TrendingUp size={20} className="text-tertiary" />
              </div>
              <div className="flex items-baseline gap-3">
                <p className="font-display text-4xl text-primary leading-tight">S/ 42,500</p>
                <span className="text-xs font-bold text-primary bg-primary/10 px-2 py-0.5 rounded-md">+12.5%</span>
              </div>
            </div>

            <div className="bg-surface-container-lowest rounded-[2rem] border border-outline-variant/30 p-8 shadow-xl shadow-primary/5">
              <div className="flex justify-between items-start mb-2">
                <h3 className="text-xs font-bold text-on-surface-variant uppercase tracking-widest">Costo de Inventario</h3>
                <Package size={20} className="text-error" />
              </div>
              <div className="flex items-baseline gap-3">
                <p className="font-display text-4xl text-on-surface leading-tight">S/ 15,200</p>
                <span className="text-xs font-bold text-error bg-error/10 px-2 py-0.5 rounded-md">-2.1%</span>
              </div>
            </div>

            <div className="rounded-[2rem] p-8 border border-primary/20 bg-primary/5 backdrop-blur-md relative overflow-hidden flex flex-col justify-end min-h-48">
              <div className="absolute -right-6 -top-6 w-32 h-32 bg-primary/10 rounded-full blur-2xl"></div>
              <AlertTriangle className="text-primary mb-4" size={32} />
              <h4 className="font-display text-2xl text-primary mb-1">Alerta de Stock</h4>
              <p className="text-sm text-on-surface-variant mb-4">3 ingredientes clave están por debajo del nivel recomendado.</p>
              <button className="text-sm font-bold text-primary underline text-left">Revisar inventario</button>
            </div>
          </div>

          <div className="col-span-12 lg:col-span-8 bg-surface-container-lowest rounded-[2rem] border border-outline-variant/30 p-8 shadow-xl shadow-primary/5 flex flex-col min-h-96">
            <div className="flex justify-between items-center mb-10">
              <h3 className="font-display text-2xl text-on-surface">Rentabilidad por Categoría</h3>
              <div className="flex bg-surface-container rounded-xl p-1">
                <button className="px-4 py-2 text-xs font-bold rounded-lg bg-primary text-on-primary">Ingresos</button>
                <button className="px-4 py-2 text-xs font-bold rounded-lg text-on-surface-variant hover:text-on-surface">Margen</button>
              </div>
            </div>
            <div className="flex-1 w-full mt-auto">
              <ResponsiveContainer width="100%" height={300}>
                <BarChart data={chartData} barGap={0}>
                  <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="#c5c8b855" />
                  <XAxis dataKey="name" axisLine={false} tickLine={false} tick={{ fontSize: 10, fontWeight: 700, fill: '#45483c' }} />
                  <YAxis axisLine={false} tickLine={false} tick={{ fontSize: 10, fontWeight: 700, fill: '#45483c' }} />
                  <Tooltip cursor={{ fill: 'transparent' }} />
                  <Bar dataKey="value" radius={[4, 4, 0, 0]} barSize={50}>
                    {chartData.map((entry, index) => (
                      <Cell key={`cell-${index}`} fill={entry.color} />
                    ))}
                  </Bar>
                </BarChart>
              </ResponsiveContainer>
            </div>
          </div>
        </div>

        <div className="mt-8 bg-surface-container-lowest rounded-[2rem] border border-outline-variant/30 shadow-xl shadow-primary/5 overflow-hidden">
          <div className="p-8 border-b border-outline-variant/30 flex justify-between items-center bg-surface-container-low/30">
            <h3 className="font-display text-2xl text-on-surface">Gestión de Disponibilidad</h3>
            <div className="relative">
              <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant" size={18} />
              <input 
                type="text" 
                placeholder="Buscar platillo..." 
                className="pl-10 pr-4 py-2 rounded-full border border-outline-variant bg-surface focus:ring-2 focus:ring-primary/20 w-64"
              />
            </div>
          </div>
          <div className="overflow-x-auto">
            <table className="w-full text-left">
              <thead>
                <tr className="bg-surface-container-low/20 border-b border-outline-variant/30">
                  <th className="py-5 px-8 text-xs font-bold text-on-surface-variant uppercase tracking-widest">Platillo</th>
                  <th className="py-5 px-8 text-xs font-bold text-on-surface-variant uppercase tracking-widest text-center">Precio</th>
                  <th className="py-5 px-8 text-xs font-bold text-on-surface-variant uppercase tracking-widest text-center">Estado</th>
                  <th className="py-5 px-8 text-xs font-bold text-on-surface-variant uppercase tracking-widest text-right">Acción</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-outline-variant/20">
                <tr className="hover:bg-surface-container-low/10 transition-colors">
                  <td className="py-6 px-8 flex items-center gap-4">
                    <div className="w-10 h-10 rounded-lg bg-surface-variant overflow-hidden border border-outline-variant/30">
                      <img src="https://lh3.googleusercontent.com/aida-public/AB6AXuCRPZKE9sWZcFtt313l56yLaoIoEoT4yC9X0SW3IkTCji-otNmxcmH1cBbuBRkwnmal8p63GNN9_QjGyuIAxy9HOj76tv4cYrjc5GbktFFq8GuYqCI7G2NA0qY4P0gW-5226gBn56_TJJ1RqYNUoqU7XPFKvFCOYPUl21XDLGFl9zP_53FljGvIQ67m66XIoVgpevrJTnR8mnikjDPgVEaRpA87G7AgtBZMqd6-OdsBEfty9_9m1UlJehYF0GBs8E5qFMn8tn6iVEQ" className="w-full h-full object-cover" />
                    </div>
                    <div>
                      <p className="font-bold text-on-surface">Ceviche Clásico</p>
                      <p className="text-[10px] text-on-surface-variant font-bold uppercase tracking-widest">Entradas</p>
                    </div>
                  </td>
                  <td className="py-6 px-8 text-center font-bold">S/ 45.00</td>
                  <td className="py-6 px-8 text-center">
                    <span className="px-3 py-1 bg-primary/10 text-primary text-[10px] font-bold rounded-full">DISPONIBLE</span>
                  </td>
                  <td className="py-6 px-8 text-right">
                    <button className="p-2 hover:bg-surface-variant rounded-lg transition-colors"><MoreVertical size={18}/></button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </main>
    </div>
  );
}
