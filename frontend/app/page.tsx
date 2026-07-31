import { TopNav } from "@/components/top-nav";
import { UploadZone } from "@/components/upload-zone";
import { PatternControl } from "@/components/pattern-control";
import { PatternResult } from "@/components/pattern-result";
import { Sparkles } from "lucide-react";

export default function StudioPage() {
  return (
    <div className="min-h-screen bg-background">
      <TopNav />

      <main className="mx-auto max-w-[1400px] px-4 py-8 md:px-8">
        {/* Page header */}
        <div className="mb-8 flex flex-col gap-3 md:flex-row md:items-end md:justify-between">
          <div className="flex flex-col gap-2">
            <span className="inline-flex w-fit items-center gap-1.5 rounded-full bg-primary/10 px-3 py-1 text-xs font-bold text-primary">
              <Sparkles className="h-3.5 w-3.5" aria-hidden="true" />
              AI STUDIO
            </span>
            <h1 className="text-2xl font-black tracking-tight text-foreground text-balance md:text-3xl">
              AI 패션 스튜디오
            </h1>
            <p className="max-w-xl text-sm leading-relaxed text-muted-foreground text-pretty">
              당신의 원화를 업로드하고, Generative AI로 세상에 하나뿐인 패션
              텍스타일 패턴을 완성하세요.
            </p>
          </div>
          <div className="flex items-center gap-6 rounded-2xl border border-border bg-card px-6 py-4 shadow-sm">
            <div className="text-center">
              <p className="text-xl font-black text-foreground">12</p>
              <p className="text-xs text-muted-foreground">등록 원화</p>
            </div>
            <div className="h-8 w-px bg-border" aria-hidden="true" />
            <div className="text-center">
              <p className="text-xl font-black text-primary">48</p>
              <p className="text-xs text-muted-foreground">생성 패턴</p>
            </div>
            <div className="h-8 w-px bg-border" aria-hidden="true" />
            <div className="text-center">
              <p className="text-xl font-black text-accent">7</p>
              <p className="text-xs text-muted-foreground">쇼룸 등록</p>
            </div>
          </div>
        </div>

        {/* Three-column workflow */}
        <div className="grid grid-cols-1 gap-6 lg:grid-cols-3">
          <UploadZone />
          <PatternControl />
          <PatternResult />
        </div>
      </main>
    </div>
  );
}
