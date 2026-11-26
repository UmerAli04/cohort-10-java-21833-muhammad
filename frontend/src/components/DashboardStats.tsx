
import {
    Users,
    UserPlus,
    ArrowUpRight,
    ArrowDownRight,
} from 'lucide-react'

interface DashboardStatsProps {
    totalContacts: number
    activeContacts: number
    newContacts: number
    inactiveContacts: number
}

function DashboardStats({
    totalContacts,
    activeContacts,
    newContacts,
    inactiveContacts,
}: DashboardStatsProps) {
    const stats = [
        {
            title: 'Total Contacts',
            value: totalContacts.toLocaleString(),
            change: '+12.5%',
            positive: true,
            icon: Users,
            iconBg: 'bg-blue-50',
            iconColor: 'text-blue-600',
        },
        {
            title: 'Active Contacts',
            value: activeContacts.toLocaleString(),
            change: '+8.2%',
            positive: true,
            icon: UserPlus,
            iconBg: 'bg-emerald-50',
            iconColor: 'text-emerald-600',
        },
        {
            title: 'New Contacts',
            value: newContacts.toLocaleString(),
            change: '+4.3%',
            positive: true,
            icon: ArrowUpRight,
            iconBg: 'bg-violet-50',
            iconColor: 'text-violet-600',
        },
        {
            title: 'Inactive Contacts',
            value: inactiveContacts.toLocaleString(),
            change: '-2.1%',
            positive: false,
            icon: Users,
            iconBg: 'bg-amber-50',
            iconColor: 'text-amber-600',
        },
    ]

    return (
        <div className="grid grid-cols-1 gap-5 sm:grid-cols-2 xl:grid-cols-4">
            {stats.map((stat) => {
                const Icon = stat.icon

                return (
                    <div
                        key={stat.title}
                        className="rounded-2xl border border-slate-200 bg-white p-5 shadow-sm transition hover:-translate-y-1 hover:shadow-md"
                    >
                        <div className="flex items-center justify-between">
                            <p className="text-sm font-medium text-slate-500">
                                {stat.title}
                            </p>

                            <div className={`flex h-11 w-11 items-center justify-center rounded-xl ${stat.iconBg} ${stat.iconColor}`}>
                                <Icon size={21} />
                            </div>
                        </div>

                        <div className="mt-5 flex items-end justify-between">
                            <h3 className="text-2xl font-bold text-slate-900">
                                {stat.value}
                            </h3>

                            <span className={`flex items-center gap-1 text-xs font-semibold ${stat.positive ? 'text-emerald-600' : 'text-rose-600'
                                }`}>
                                {stat.positive ? (
                                    <ArrowUpRight size={14} />
                                ) : (
                                    <ArrowDownRight size={14} />
                                )}
                                {stat.change}
                            </span>
                        </div>

                        <p className="mt-2 text-xs text-slate-400">
                            Compared to last month
                        </p>
                    </div>
                )
            })}
        </div>
    )
}

export default DashboardStats
