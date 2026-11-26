
import {
    Search,
    SlidersHorizontal,
    Download,
    Mail,
    Phone,
    MoreHorizontal,
} from 'lucide-react'

interface Contact {
    id: number
    name: string
    email: string
    company: string
    phone: string
    status: string
    date: string
    initials: string
    color: string
}

interface RecentContactsProps {
    search: string
    setSearch: (value: string) => void
    filteredContacts: Contact[]
}

function RecentContacts({
    search,
    setSearch,
    filteredContacts,
}: RecentContactsProps) {
    return (
        <section className="mt-6 overflow-hidden rounded-2xl border border-slate-200/80 bg-white shadow-sm">
            <div className="flex flex-col justify-between gap-4 border-b border-slate-100 p-5 md:flex-row md:items-center md:p-6">
                <div>
                    <div className="flex items-center gap-2">
                        <h3 className="font-bold text-slate-900">Recent contacts</h3>
                        <span className="rounded-full bg-blue-50 px-2 py-0.5 text-[10px] font-bold text-blue-700">
                            2,450 total
                        </span>
                    </div>
                    <p className="mt-1 text-xs text-slate-400">
                        Manage and organize your latest contacts.
                    </p>
                </div>

                <div className="flex flex-wrap gap-2">
                    <div className="flex items-center gap-2 rounded-lg border border-slate-200 px-3 py-2 sm:hidden">
                        <Search size={15} className="text-slate-400" />
                        <input
                            value={search}
                            onChange={(e) => setSearch(e.target.value)}
                            placeholder="Search..."
                            className="w-full min-w-0 text-xs outline-none"
                        />
                    </div>

                    <button className="flex items-center gap-2 rounded-lg border border-slate-200 px-3 py-2 text-xs font-semibold text-slate-600 hover:bg-slate-50">
                        <SlidersHorizontal size={14} /> Filters
                    </button>

                    <button className="flex items-center gap-2 rounded-lg border border-slate-200 px-3 py-2 text-xs font-semibold text-slate-600 hover:bg-slate-50">
                        <Download size={14} /> Export
                    </button>
                </div>
            </div>

            <div className="overflow-x-auto">
                <table className="w-full min-w-[700px] text-left">
                    <thead className="bg-slate-50/80">
                        <tr className="text-[10px] font-bold uppercase tracking-wider text-slate-400">
                            <th className="px-6 py-3.5">Contact</th>
                            <th className="px-5 py-3.5">Company</th>
                            <th className="px-5 py-3.5">Phone</th>
                            <th className="px-5 py-3.5">Status</th>
                            <th className="px-5 py-3.5">Date added</th>
                            <th className="px-5 py-3.5"></th>
                        </tr>
                    </thead>

                    <tbody className="divide-y divide-slate-100">
                        {filteredContacts.map((contact) => (
                            <tr
                                key={contact.id}
                                className="transition hover:bg-slate-50/70"
                            >
                                <td className="px-6 py-4">
                                    <div className="flex items-center gap-3">
                                        <div className={`flex h-10 w-10 items-center justify-center rounded-full text-xs font-bold ${contact.color}`}>
                                            {contact.initials}
                                        </div>
                                        <div>
                                            <p className="text-sm font-semibold text-slate-800">
                                                {contact.name}
                                            </p>
                                            <p className="mt-0.5 flex items-center gap-1 text-xs text-slate-400">
                                                <Mail size={11} />
                                                {contact.email}
                                            </p>
                                        </div>
                                    </div>
                                </td>

                                <td className="px-5 py-4 text-sm font-medium text-slate-600">
                                    {contact.company}
                                </td>

                                <td className="px-5 py-4">
                                    <span className="flex items-center gap-1.5 text-xs text-slate-500">
                                        <Phone size={12} />
                                        {contact.phone}
                                    </span>
                                </td>

                                <td className="px-5 py-4">
                                    <span className={`rounded-full px-2.5 py-1 text-[10px] font-bold ${contact.status === 'Active'
                                            ? 'bg-emerald-50 text-emerald-700'
                                            : 'bg-slate-100 text-slate-500'
                                        }`}>
                                        {contact.status}
                                    </span>
                                </td>

                                <td className="px-5 py-4 text-xs text-slate-500">
                                    {contact.date}
                                </td>

                                <td className="px-5 py-4">
                                    <button className="rounded-lg p-2 text-slate-400 hover:bg-slate-100">
                                        <MoreHorizontal size={18} />
                                    </button>
                                </td>
                            </tr>
                        ))}

                        {filteredContacts.length === 0 && (
                            <tr>
                                <td colSpan={6} className="px-6 py-10 text-center text-sm text-slate-400">
                                    No contacts match your search.
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>

            <div className="flex flex-col justify-between gap-3 border-t border-slate-100 px-5 py-4 sm:flex-row sm:items-center">
                <p className="text-xs text-slate-400">
                    Showing <span className="font-semibold text-slate-700">
                        {filteredContacts.length}
                    </span> sample contacts
                </p>

                <div className="flex items-center gap-2">
                    <button className="rounded-lg border border-slate-200 px-3 py-2 text-xs font-semibold text-slate-500 hover:bg-slate-50">
                        Previous
                    </button>
                    <button className="rounded-lg bg-blue-600 px-3 py-2 text-xs font-semibold text-white">
                        1
                    </button>
                    <button className="rounded-lg border border-slate-200 px-3 py-2 text-xs font-semibold text-slate-500 hover:bg-slate-50">
                        2
                    </button>
                    <button className="rounded-lg border border-slate-200 px-3 py-2 text-xs font-semibold text-slate-500 hover:bg-slate-50">
                        Next
                    </button>
                </div>
            </div>
        </section>
    )
}

export default RecentContacts
