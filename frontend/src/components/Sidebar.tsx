
import {
    Users,
    LayoutDashboard,
    UserPlus,
    Settings,
    HelpCircle,
    ArrowUpRight,
    Sparkles,
    MoreHorizontal,
    X,
} from 'lucide-react'

interface SidebarProps {
    activeNav: string
    setActiveNav: (value: string) => void
    mobileMenu: boolean
    setMobileMenu: (value: boolean) => void
}

const navigation = [
    { label: 'Overview', icon: LayoutDashboard },
    { label: 'Contacts', icon: Users },
    { label: 'Add Contact', icon: UserPlus },
]

function Sidebar({
    activeNav,
    setActiveNav,
    mobileMenu,
    setMobileMenu,
}: SidebarProps) {
    return (
        <aside
            className={`${mobileMenu ? 'flex' : 'hidden'
                } fixed inset-y-0 left-0 z-50 w-[260px] flex-col border-r border-slate-200 bg-white md:flex md:static md:z-auto`}
        >
            <div className="flex h-[76px] items-center justify-between border-b border-slate-100 px-6">
                <div className="flex items-center gap-3">
                    <div className="flex h-10 w-10 items-center justify-center rounded-xl bg-blue-600 text-white shadow-lg shadow-blue-200">
                        <Users size={22} />
                    </div>
                    <div>
                        <h1 className="text-[17px] font-bold tracking-tight">Connectly</h1>
                        <p className="text-[10px] font-semibold uppercase tracking-[2px] text-slate-400">
                            Contact Manager
                        </p>
                    </div>
                </div>

                <button
                    onClick={() => setMobileMenu(false)}
                    className="text-slate-400 md:hidden"
                >
                    <X size={20} />
                </button>
            </div>

            <div className="px-4 pt-8">
                <p className="mb-3 px-3 text-[10px] font-bold uppercase tracking-[1.6px] text-slate-400">
                    Workspace
                </p>

                <nav className="space-y-1">
                    {navigation.map(({ label, icon: Icon }) => (
                        <button
                            key={label}
                            onClick={() => {
                                setActiveNav(label)
                                setMobileMenu(false)
                            }}
                            className={`flex w-full items-center gap-3 rounded-xl px-3 py-3 text-sm font-semibold transition ${activeNav === label
                                    ? 'bg-blue-50 text-blue-700'
                                    : 'text-slate-500 hover:bg-slate-50 hover:text-slate-900'
                                }`}
                        >
                            <Icon size={19} />
                            {label}

                            {label === 'Contacts' && (
                                <span className="ml-auto rounded-md bg-slate-100 px-2 py-0.5 text-xs text-slate-500">
                                    128
                                </span>
                            )}
                        </button>
                    ))}
                </nav>
            </div>

            <div className="mt-9 px-4">
                <p className="mb-3 px-3 text-[10px] font-bold uppercase tracking-[1.6px] text-slate-400">
                    Preferences
                </p>

                <button className="flex w-full items-center gap-3 rounded-xl px-3 py-3 text-sm font-semibold text-slate-500 hover:bg-slate-50">
                    <Settings size={19} /> Settings
                </button>

                <button className="flex w-full items-center gap-3 rounded-xl px-3 py-3 text-sm font-semibold text-slate-500 hover:bg-slate-50">
                    <HelpCircle size={19} /> Help & Support
                </button>
            </div>

            <div className="mt-auto p-4">
                <div className="rounded-2xl bg-slate-900 p-4 text-white">
                    <div className="mb-3 flex h-9 w-9 items-center justify-center rounded-xl bg-white/10">
                        <Sparkles size={19} className="text-blue-300" />
                    </div>

                    <p className="text-sm font-semibold">
                        Keep everything connected
                    </p>
                    <p className="mt-1 text-xs leading-5 text-slate-400">
                        Manage your relationships from one simple workspace.
                    </p>

                    <button className="mt-4 flex items-center gap-2 text-xs font-semibold text-blue-300">
                        Explore workspace <ArrowUpRight size={14} />
                    </button>
                </div>

                <div className="mt-4 flex items-center gap-3 border-t border-slate-100 pt-4">
                    <div className="flex h-10 w-10 items-center justify-center rounded-full bg-blue-100 text-sm font-bold text-blue-700">
                        UA
                    </div>

                    <div className="min-w-0 flex-1">
                        <p className="truncate text-sm font-bold">Umer Ali</p>
                        <p className="truncate text-xs text-slate-400">Administrator</p>
                    </div>

                    <MoreHorizontal size={20} className="text-slate-400" />
                </div>
            </div>
        </aside>
    )
}

export default Sidebar
