"use client";

import { useState } from "react";
import { UploadCloud, ImageIcon, FileCheck2 } from "lucide-react";

export function UploadZone() {
  const [dragging, setDragging] = useState(false);

  return (
    <section
      className="flex flex-col gap-5 rounded-2xl border border-border bg-card p-6 shadow-sm"
      aria-labelledby="upload-heading"
    >
      <div className="flex items-center gap-2">
        <span className="flex h-7 w-7 items-center justify-center rounded-lg bg-primary/10 text-xs font-bold text-primary">
          01
        </span>
        <h2 id="upload-heading" className="text-base font-bold text-foreground">
          원화 업로드
        </h2>
      </div>

      {/* Drop zone */}
      <div
        onDragOver={(e) => {
          e.preventDefault();
          setDragging(true);
        }}
        onDragLeave={() => setDragging(false)}
        onDrop={(e) => {
          e.preventDefault();
          setDragging(false);
        }}
        className={`flex flex-col items-center justify-center gap-3 rounded-xl border-2 border-dashed px-4 py-10 text-center transition-colors ${
          dragging
            ? "border-primary bg-primary/5"
            : "border-border bg-muted/40 hover:border-primary/50"
        }`}
      >
        <div className="flex h-12 w-12 items-center justify-center rounded-full bg-primary/10">
          <UploadCloud className="h-6 w-6 text-primary" aria-hidden="true" />
        </div>
        <p className="text-sm font-semibold text-foreground">
          원화 파일 드래그 &amp; 드롭
        </p>
        <p className="text-xs text-muted-foreground">
          또는 클릭하여 업로드 · PNG, JPG, SVG (최대 20MB)
        </p>
        <button
          type="button"
          className="mt-1 rounded-full border border-border bg-card px-4 py-2 text-xs font-bold text-foreground transition-colors hover:bg-muted"
        >
          파일 선택
        </button>
      </div>

      {/* Preview */}
      <div className="flex items-center gap-3 rounded-xl border border-border bg-muted/40 p-3">
        <div className="relative h-16 w-16 shrink-0 overflow-hidden rounded-lg border border-border bg-card">
          {/* eslint-disable-next-line @next/next/no-img-element */}
          <img
            src="/patterns/original-sketch.png"
            alt="업로드된 원화 미리보기, 단청 전통 스케치"
            className="h-full w-full object-cover"
          />
        </div>
        <div className="min-w-0 flex-1">
          <p className="flex items-center gap-1.5 truncate text-sm font-semibold text-foreground">
            <ImageIcon className="h-4 w-4 shrink-0 text-primary" aria-hidden="true" />
            dancheong_sketch_01.png
          </p>
          <p className="text-xs text-muted-foreground">3.2 MB · 2048 × 2048</p>
        </div>
        <span className="flex items-center gap-1 rounded-full bg-primary/10 px-2.5 py-1 text-xs font-bold text-primary">
          <FileCheck2 className="h-3.5 w-3.5" aria-hidden="true" />
          완료
        </span>
      </div>

      {/* Inputs */}
      <div className="flex flex-col gap-4">
        <div className="flex flex-col gap-2">
          <label htmlFor="title" className="text-sm font-semibold text-foreground">
            작품 제목
          </label>
          <input
            id="title"
            type="text"
            defaultValue="단청 전통 스케치"
            placeholder="작품 제목을 입력하세요"
            className="rounded-xl border border-input bg-card px-4 py-2.5 text-sm text-foreground outline-none transition-shadow placeholder:text-muted-foreground focus:ring-2 focus:ring-ring"
          />
        </div>
        <div className="flex flex-col gap-2">
          <label htmlFor="note" className="text-sm font-semibold text-foreground">
            작품 설명 / 아티스트 노트
          </label>
          <textarea
            id="note"
            rows={4}
            placeholder="작품에 담긴 이야기와 영감을 자유롭게 적어주세요."
            className="resize-none rounded-xl border border-input bg-card px-4 py-2.5 text-sm leading-relaxed text-foreground outline-none transition-shadow placeholder:text-muted-foreground focus:ring-2 focus:ring-ring"
          />
        </div>
      </div>
    </section>
  );
}
