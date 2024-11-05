import classNames from 'classnames';
import type { FC } from 'react';

interface LoadingSpecProps {
  withError?: boolean;
}

export const LoadingSpec: FC<LoadingSpecProps> = ({ withError = false }) => (
  <div className={classNames('mt-12 text-center', { 'text-red-600': withError })}>
    <h2>{withError ? 'An error occurred. Try reloading the page.' : 'Loading API spec...'}</h2>
  </div>
);
