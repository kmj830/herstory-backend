import { Bell, Sparkles } from "lucide-react";

const navLinks = [
  { label: "홈", active: false },
  { label: "AI 스튜디오", active: true },
  { label: "3D 쇼룸", active: false },
  { label: "O2O 팝업", active: false },
  { label: "마이페이지", active: false },
];

export function TopNav() {
  return (
    <header className="sticky top-0 z-30 w-full border-b border-border bg-card/80 backdrop-blur-md">
      <div className="mx-auto flex h-16 max-w-[1400px] items-center justify-between gap-4 px-4 md:px-8">
        {/* Brand */}
        <div className="flex items-center gap-2">
          <div className="flex h-8 w-8 items-center justify-center rounded-md bg-primary">
            <Sparkles className="h-4 w-4 text-primary-foreground" aria-hidden="true" />
          </div>
          <span className="text-lg font-black tracking-tight text-foreground">
            HER-STORY
          </span>
        </div>

        {/* Nav links */}
        <nav className="hidden items-center gap-1 md:flex" aria-label="주요 메뉴">
          {navLinks.map((link) => (
            <a
              key={link.label}
              href="#"
              aria-current={link.active ? "page" : undefined}
              className={
                link.active
                  ? "rounded-full bg-primary/10 px-4 py-2 text-sm font-bold text-primary"
                  : "rounded-full px-4 py-2 text-sm font-medium text-muted-foreground transition-colors hover:bg-muted hover:text-foreground"
              }
            >
              {link.label}
            </a>
          ))}
        </nav>

        {/* Actions */}
        <div className="flex items-center gap-3">
          <button
            type="button"
            className="relative flex h-10 w-10 items-center justify-center rounded-full text-muted-foreground transition-colors hover:bg-muted hover:text-foreground"
            aria-label="알림 3개"
          >
            <Bell className="h-5 w-5" aria-hidden="true" />
            <span className="absolute -right-0.5 -top-0.5 flex h-5 w-5 items-center justify-center rounded-full bg-accent text-[11px] font-bold text-accent-foreground">
              3
            </span>
          </button>
          <div className="flex items-center gap-2">
            <div
              className="flex h-10 w-10 items-center justify-center rounded-full bg-primary text-sm font-bold text-primary-foreground"
              aria-hidden="true"
            >
              JY
            </div>
            <div className="hidden leading-tight sm:block">
              <p className="text-sm font-bold text-foreground">정유리</p>
              <p className="text-xs text-muted-foreground">아티스트</p>
            </div>
          </div>
        </div>
      </div>
    </header>
  );
}
