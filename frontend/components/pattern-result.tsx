import { Box, Bookmark, Download } from "lucide-react";

export function PatternResult() {
  return (
    <section
      className="flex flex-col gap-5 rounded-2xl border border-border bg-card p-6 shadow-sm"
      aria-labelledby="result-heading"
    >
      <div className="flex items-center justify-between gap-2">
        <div className="flex items-center gap-2">
          <span className="flex h-7 w-7 items-center justify-center rounded-lg bg-accent/10 text-xs font-bold text-accent">
            03
          </span>
          <h2 id="result-heading" className="text-base font-bold text-foreground">
            AI 패턴 결과
          </h2>
        </div>
        <span className="rounded-full bg-primary/10 px-2.5 py-1 text-xs font-bold text-primary">
          생성 완료
        </span>
      </div>

      {/* Preview image */}
      <div className="overflow-hidden rounded-xl border border-border bg-muted/40">
        <div className="relative aspect-square w-full">
          {/* eslint-disable-next-line @next/next/no-img-element */}
          <img
            src="/patterns/dancheong-pattern.png"
            alt="생성된 고해상도 텍스타일 패턴: 단청 문양과 모던 네온 그래픽의 융합"
            className="h-full w-full object-cover"
          />
        </div>
      </div>

      {/* Metadata */}
      <dl className="grid grid-cols-2 gap-3">
        <div className="rounded-xl border border-border bg-muted/40 p-3">
          <dt className="text-xs text-muted-foreground">패턴명</dt>
          <dd className="mt-0.5 truncate text-sm font-bold text-foreground">
            단청 네온 퓨전
          </dd>
        </div>
        <div className="rounded-xl border border-border bg-muted/40 p-3">
          <dt className="text-xs text-muted-foreground">생성 일시</dt>
          <dd className="mt-0.5 text-sm font-bold text-foreground">
            2026.08.01 14:32
          </dd>
        </div>
      </dl>

      {/* Actions */}
      <div className="mt-auto flex flex-col gap-3 pt-1">
        <button
          type="button"
          className="flex items-center justify-center gap-2 rounded-xl bg-primary px-6 py-3.5 text-sm font-bold text-primary-foreground shadow-sm transition-all hover:brightness-110 hover:shadow-md focus:outline-none focus:ring-2 focus:ring-ring focus:ring-offset-2 focus:ring-offset-card"
        >
          <Box className="h-5 w-5" aria-hidden="true" />
          3D 쇼룸에 상품 등록
        </button>
        <div className="grid grid-cols-2 gap-3">
          <button
            type="button"
            className="flex items-center justify-center gap-2 rounded-xl border border-border bg-card px-4 py-3 text-sm font-bold text-foreground transition-colors hover:bg-muted"
          >
            <Bookmark className="h-4 w-4 text-primary" aria-hidden="true" />
            컬렉션 저장
          </button>
          <button
            type="button"
            className="flex items-center justify-center gap-2 rounded-xl border border-border bg-card px-4 py-3 text-sm font-bold text-foreground transition-colors hover:bg-muted"
          >
            <Download className="h-4 w-4 text-primary" aria-hidden="true" />
            다운로드
          </button>
        </div>
      </div>
    </section>
  );
}
