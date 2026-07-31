"use client";

import { useState } from "react";
import { Sparkles, Wand2 } from "lucide-react";

const presets = ["단청 민화", "모던 미니멀", "비비드 실크", "친환경 ESG"];

export function PatternControl() {
  const [active, setActive] = useState<string[]>(["단청 민화"]);

  function toggle(preset: string) {
    setActive((prev) =>
      prev.includes(preset)
        ? prev.filter((p) => p !== preset)
        : [...prev, preset],
    );
  }

  return (
    <section
      className="flex flex-col gap-5 rounded-2xl border border-border bg-card p-6 shadow-sm"
      aria-labelledby="control-heading"
    >
      <div className="flex items-center gap-2">
        <span className="flex h-7 w-7 items-center justify-center rounded-lg bg-primary/10 text-xs font-bold text-primary">
          02
        </span>
        <h2 id="control-heading" className="text-base font-bold text-foreground">
          AI 패턴 생성
        </h2>
      </div>

      {/* Prompt */}
      <div className="flex flex-col gap-2">
        <label htmlFor="prompt" className="text-sm font-semibold text-foreground">
          AI 프롬프트 입력
        </label>
        <textarea
          id="prompt"
          rows={6}
          defaultValue="한국 전통 단청 문양과 모던 네온 그래픽의 융합"
          placeholder="생성하고 싶은 AI 패션 패턴 스타일을 입력하세요 (예: 한국 전통 단청 문양과 모던 네온 그래픽의 융합)"
          className="resize-none rounded-xl border border-input bg-card px-4 py-3 text-sm leading-relaxed text-foreground outline-none transition-shadow placeholder:text-muted-foreground focus:ring-2 focus:ring-ring"
        />
      </div>

      {/* Presets */}
      <div className="flex flex-col gap-2.5">
        <p className="text-sm font-semibold text-foreground">스타일 프리셋</p>
        <div className="flex flex-wrap gap-2">
          {presets.map((preset) => {
            const isActive = active.includes(preset);
            return (
              <button
                key={preset}
                type="button"
                aria-pressed={isActive}
                onClick={() => toggle(preset)}
                className={
                  isActive
                    ? "rounded-full border border-primary bg-primary/10 px-3.5 py-1.5 text-xs font-bold text-primary transition-colors"
                    : "rounded-full border border-border bg-card px-3.5 py-1.5 text-xs font-medium text-muted-foreground transition-colors hover:border-primary/40 hover:text-foreground"
                }
              >
                {preset}
              </button>
            );
          })}
        </div>
      </div>

      <div className="mt-auto flex flex-col gap-3 pt-2">
        <div className="flex items-center gap-2 rounded-xl bg-muted/50 px-4 py-3 text-xs text-muted-foreground">
          <Wand2 className="h-4 w-4 shrink-0 text-primary" aria-hidden="true" />
          <span className="leading-relaxed">
            원화의 선과 색을 분석해 텍스타일 패턴으로 재해석합니다.
          </span>
        </div>

        {/* Primary CTA */}
        <button
          type="button"
          className="group flex items-center justify-center gap-2 rounded-xl bg-gradient-to-r from-primary to-accent px-6 py-3.5 text-sm font-bold text-primary-foreground shadow-sm transition-all hover:brightness-110 hover:shadow-md focus:outline-none focus:ring-2 focus:ring-ring focus:ring-offset-2 focus:ring-offset-card"
        >
          <Sparkles
            className="h-5 w-5 transition-transform group-hover:scale-110"
            aria-hidden="true"
          />
          AI 패턴 생성하기
        </button>
      </div>
    </section>
  );
}
