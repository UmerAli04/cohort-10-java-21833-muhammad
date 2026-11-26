
import { ArrowUpRight } from 'lucide-react'

function ContactActivity() {
    const activities = [
        { label: 'Active', value: '72%', color: 'bg-blue-600' },
        { label: 'Pending', value: '16%', color: 'bg-violet-400' },
        { label: 'Inactive', value: '12%', color: 'bg-slate-200' },
    ]

    return (
        <section className="rounded-2xl border border-slate-200/80 bg-white p-5 shadow-sm">
            <div>
                <h3 className="font-bold text-slate-900">Contact Activity</h3>
                <p className="mt-1 text-xs text-slate-400">
                    Current contact status breakdown
                </p>
            </div>

            <div className="mt-8 flex items-center justify-center">
                <div
                    className="relative flex h-44 w-44 items-center justify-center rounded-full"
                    style={{
                        background: 'conic-gradient(#2563eb 0% 72%, #a78bfa 72% 88%, #e2e8f0 88% 100%)',
                    }}
                >
                    <div className="flex h-32 w-32 flex-col items-center justify-center rounded-full bg-white">
                        <span className="text-2xl font-bold text-slate-900">2,450</span>
                        <span className="mt-1 text-xs text-slate-400">Total contacts</span>
                    </div>
                </div>
            </div>

            <div className="mt-8 space-y-4">
                {activities.map((activity) => (
                    <div key={activity.label} className="flex items-center justify-between">
                        <div className="flex items-center gap-2">
                            <span className={`h-2.5 w-2.5 rounded-full ${activity.color}`} />
                            <span className="text-sm font-medium text-slate-600">
                                {activity.label}
                            </span>
                        </div>

                        <div className="flex items-center gap-3">
                            <span className="text-sm font-bold text-slate-900">
                                {activity.value}
                            </span>
                        </div>
                    </div>
                ))}
            </div>

            <div className="mt-6 flex items-center gap-2 rounded-xl bg-blue-50 p-3 text-xs text-blue-700">
                <ArrowUpRight size={15} />
                <span>77% of your contacts are currently active</span>
            </div>
        </section>
    )
}

export default ContactActivity
