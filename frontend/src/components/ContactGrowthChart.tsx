
import { ArrowUpRight, CalendarDays, ChevronDown } from 'lucide-react'

function ContactGrowthChart() {
    const chartData = [
        { month: 'Jan', value: 42 },
        { month: 'Feb', value: 58 },
        { month: 'Mar', value: 48 },
        { month: 'Apr', value: 72 },
        { month: 'May', value: 62 },
        { month: 'Jun', value: 85 },
        { month: 'Jul', value: 68 },
        { month: 'Aug', value: 92 },
        { month: 'Sep', value: 76 },
        { month: 'Oct', value: 100 },
        { month: 'Nov', value: 82 },
        { month: 'Dec', value: 95 },
    ]

    return (
        <div className="rounded-2xl border border-slate-200/80 bg-white p-5 shadow-sm sm:p-6">
            <div className="flex flex-wrap items-start justify-between gap-4">
                <div>
                    <h3 className="text-base font-bold text-slate-900">
                        Contact Growth
                    </h3>
                    <p className="mt-1 text-xs text-slate-400">
                        Overview of your contact acquisition
                    </p>
                </div>

                <button className="flex items-center gap-2 rounded-lg border border-slate-200 px-3 py-2 text-xs font-medium text-slate-600">
                    <CalendarDays size={14} />
                    This year
                    <ChevronDown size={14} />
                </button>
            </div>

            <div className="mt-6 flex items-center gap-2">
                <h2 className="text-2xl font-bold text-slate-900">2,450</h2>
                <span className="flex items-center gap-1 rounded-md bg-emerald-50 px-2 py-1 text-[11px] font-semibold text-emerald-600">
                    <ArrowUpRight size={13} />
                    12.5%
                </span>
            </div>

            <p className="mt-1 text-xs text-slate-400">Total contacts this year</p>

            <div className="mt-8 flex h-52 items-end gap-2 sm:gap-3">
                {chartData.map((item) => (
                    <div
                        key={item.month}
                        className="group flex h-full flex-1 flex-col items-center justify-end gap-2"
                    >
                        <div className="relative flex w-full flex-1 items-end">
                            <div
                                title={`${item.month}: ${item.value}%`}
                                style={{ height: `${item.value}%` }}
                                className={`w-full rounded-t-md transition-all duration-300 group-hover:opacity-75 ${item.month === 'Oct' ? 'bg-blue-600' : 'bg-blue-100'
                                    }`} />
                        </div>

                        <span className="text-[10px] font-medium text-slate-400">
                            {item.month}
                        </span>
                    </div>
                ))}
            </div>
        </div>
    )
}

export default ContactGrowthChart
