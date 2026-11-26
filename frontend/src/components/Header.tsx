
import {
    Search,
    Bell,
    Menu,
    Sun,
    Moon,
} from 'lucide-react'

interface HeaderProps {
    activeNav: string
    search: string
    setSearch: (value: string) => void
    setMobileMenu: (value: boolean) => void
    isDarkMode: boolean
    setIsDarkMode: (value: boolean) => void
}

function Header({
    activeNav,
    search,
    setSearch,
    setMobileMenu,
    isDarkMode,
    setIsDarkMode,
}: HeaderProps) {
    return (
        <header className="sticky top-0 z-30 flex h-[76px] items-center justify-between border-b border-slate-200 bg-white/90 px-5 backdrop-blur-xl md:px-9">
            <div className="flex items-center gap-3">
                <button
                    onClick={() => setMobileMenu(true)}
                    className="text-slate-500 md:hidden"
                >
                    <Menu size={22} />
                </button>

                <div>
                    <p className="text-xs text-slate-400">
                        Workspace / {activeNav}
                    </p>
                    <p className="mt-0.5 text-sm font-bold text-slate-800">
                        {activeNav}
                    </p>
                </div>
            </div>

            <div className="flex items-center gap-3">
                <div className="hidden items-center gap-2 rounded-xl border border-slate-200 bg-slate-50 px-3 py-2.5 sm:flex">
                    <Search size={16} className="text-slate-400" />

                    <input
                        value={search}
                        onChange={(e) => setSearch(e.target.value)}
                        placeholder="Search contacts..."
                        className="w-36 bg-transparent text-xs outline-none placeholder:text-slate-400"
                    />

                    <kbd className="rounded border border-slate-200 bg-white px-1.5 py-0.5 text-[10px] text-slate-400">
                        ⌘ K
                    </kbd>
                </div>

                <button
                    onClick={() => setIsDarkMode(!isDarkMode)}
                    title={isDarkMode ? 'Switch to Light Mode' : 'Switch to Dark Mode'}
                    aria-label={isDarkMode ? 'Switch to Light Mode' : 'Switch to Dark Mode'}
                    className="flex h-10 w-10 items-center justify-center rounded-xl border border-slate-200 text-slate-500 transition hover:bg-slate-50"
                >
                    {isDarkMode ? <Sun size={18} /> : <Moon size={18} />}
                </button>

                <button
                    aria-label="Notifications"
                    className="relative flex h-10 w-10 items-center justify-center rounded-xl border border-slate-200 text-slate-500 hover:bg-slate-50"
                >
                    <Bell size={18} />
                    <span className="absolute right-2 top-2 h-2 w-2 rounded-full border-2 border-white bg-blue-600" />
                </button>

                <div className="flex h-10 w-10 items-center justify-center rounded-full bg-blue-100 text-xs font-bold text-blue-700 md:hidden">
                    UA
                </div>
            </div>
        </header>
    )
}

export default Header
